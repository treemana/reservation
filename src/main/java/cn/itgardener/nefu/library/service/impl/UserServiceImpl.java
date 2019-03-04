/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.impl;

import cn.itgardener.nefu.library.common.ErrorMessage;
import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.TokenUtil;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.UserVo;
import cn.itgardener.nefu.library.service.UserService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.itgardener.nefu.library.common.GlobalConst.USER_DISABLE;

/**
 * @author : Jimi,pc CMY
 * @date : 2018/10/27
 * @since : Java 8
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserMapper userMapper;
    private final RedisDao redisDao;
    private final ReservationServiceImpl reservationService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisDao redisDao, ReservationServiceImpl reservationService) {
        this.userMapper = userMapper;
        this.redisDao = redisDao;
        this.reservationService = reservationService;
    }

    @Override
    public Map<String, Object> postLogin(User user) throws LibException {
        Map<String, Object> rtv = null;
        List<User> users = userMapper.selectByCondition(user);
        if (null != users && 1 == users.size()) {
            user = users.get(0);
            int type = user.getType();
            if (USER_DISABLE == type) {
                throw new LibException("当前用户已被禁用!");
            } else {
                user.setToken(TokenUtil.getToken());
                if (0 < userMapper.updateTokenBySystemId(user)) {
                    rtv = new HashMap<>(3);
                    user = users.get(0);
                    rtv.put("systemId", user.getSystemId());
                    rtv.put("token", user.getToken());
                    rtv.put("type", user.getType());
                }
            }
        } else {
            throw new LibException("用户名或密码不正确!");
        }
        return rtv;
    }

    @Override
    public List<Map<String, Object>> getBlackList() throws LibException {
        List<Map<String, Object>> rtv = new ArrayList<>();
        List<User> users = userMapper.selectByType();
        if (null != users) {
            for (User user : users) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("studentId", user.getStudentId());
                map.put("name", user.getStudentName());
                rtv.add(map);
            }
        } else {
            throw new LibException("黑名单为空");
        }
        return rtv;
    }

    @Override
    public boolean deleteBlackListByStudentId(User user) {
        return 0 < userMapper.deleteBlackListByStudentId(user);
    }

    @Override
    public int getStatus(UserVo userVo) throws LibException {
        try {
            reservationService.verifyCode(userVo.getVerifyCode(), userVo.getStudentId());
            if (!redisDao.isMember("finish", userVo.getStudentId())) {
                int currentCount = Integer.parseInt(redisDao.get("l_" + userVo.getStudentId()).split(",")[1]);
                int popCount = Integer.parseInt(redisDao.get("popCount"));
                if (currentCount - popCount < 0) {
                    throw new LibException("用户已分配");
                }
                return currentCount - popCount;
            } else {
                throw new LibException("用户已分配");
            }
        } catch (LibException e) {
            throw e;
        }
    }

    @Override
    public RestData postStudent(String fileName) {

        InputStream is;
        XSSFWorkbook xwb;
        try {
            // 构造 XSSFWorkbook 对象,strPath 传入文件路径
            is = new FileInputStream(fileName);
            xwb = new XSSFWorkbook(is);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return new RestData(1, ErrorMessage.UPLOAD_ERROR);
        }

        // 读取第一张表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        // 定义 row
        XSSFRow row;

        User user = new User();
        user.setType(0);
        String studentId;
        String studentName;
        String password;
        XSSFCell cell;
        // 循环输出表格中的内容
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            int j = row.getFirstCellNum();
            user.setSystemId(null);

            // 获取学号
            studentId = row.getCell(j).toString();
            if (0 < userMapper.selectByStudentId(studentId).size()) {
                continue;
            }
            user.setStudentId(studentId);

            // 获取姓名
            cell = row.getCell(j + 1);
            if (null == cell || 1 > cell.toString().length()) {
                studentName = "未导入";
            } else {
                studentName = cell.toString();
            }
            user.setStudentName(studentName);

            // 获取密码
            cell = row.getCell(j + 2);
            if (null == cell || 6 > cell.toString().length()) {
                password = "123456";
            } else {
                password = cell.toString().toUpperCase();
                password = password.substring(password.length() - 6);
            }
            user.setUserPassword(password);

            userMapper.insert(user);
        }

        return new RestData(true);
    }

    @Override
    public void deleteStudent() {
        userMapper.deleteAllStudent();
    }

    @Override
    public int postAddBlackList(User user) {
        return userMapper.updateTypeByStudentId(user);
    }
}
