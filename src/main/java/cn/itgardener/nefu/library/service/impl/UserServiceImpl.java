/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.impl;

import cn.itgardener.nefu.library.common.GlobalConst;
import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.util.TokenUtil;
import cn.itgardener.nefu.library.core.mapper.ConfigMapper;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.UserVo;
import cn.itgardener.nefu.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.itgardener.nefu.library.common.GlobalConst.USER_DISABLE;
import static cn.itgardener.nefu.library.common.GlobalConst.USER_STUDENT;

/**
 * @author : Jimi,pc CMY
 * @date : 2018/10/27
 * @since : Java 8
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RedisDao redisDao;
    private final ConfigMapper configMapper;
    private final ReservationServiceImpl reservationService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisDao redisDao, ConfigMapper configMapper, ReservationServiceImpl reservationService) {
        this.userMapper = userMapper;
        this.redisDao = redisDao;
        this.configMapper = configMapper;
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
                if (USER_STUDENT == type) {

                    Config config = new Config();
                    config.setConfigKey(GlobalConst.START_GRADE);
                    int startGrade = Integer.parseInt(configMapper.selectOpenGrade(config).getConfigValue());
                    config.setConfigKey(GlobalConst.END_GRADE);
                    int endGrade = Integer.parseInt(configMapper.selectOpenGrade(config).getConfigValue());
                    int studentGrade = Integer.parseInt(user.getStudentId().substring(0, 4));
                    if (studentGrade < startGrade || studentGrade > endGrade) {
                        if (startGrade == endGrade) {
                            throw new LibException("仅对" + startGrade + "级开放!");
                        } else {
                            throw new LibException("仅对" + startGrade + "-" + endGrade + "级开放!");
                        }
                    }
                }
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
    public int postAddBlackList(User user) {
        return userMapper.updateTypeByStudentId(user);
    }
}
