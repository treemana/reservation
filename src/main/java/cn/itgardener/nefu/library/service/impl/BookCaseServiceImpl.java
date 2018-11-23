/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.impl;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.Page;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.PageUtil;
import cn.itgardener.nefu.library.core.mapper.BookCaseMapper;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.BookCase;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.ShipVo;
import cn.itgardener.nefu.library.service.BookCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : chenchenT CMY
 * @date : 2018/10/28
 * @since : Java 8
 */
@Service
public class BookCaseServiceImpl implements BookCaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookCaseMapper bookCaseMapper;

    private final UserMapper userMapper;

    private final RedisDao redisDao;

    @Autowired
    public BookCaseServiceImpl(BookCaseMapper bookCaseMapper, UserMapper userMapper, RedisDao redisDao) {
        this.bookCaseMapper = bookCaseMapper;
        this.userMapper = userMapper;
        this.redisDao = redisDao;
    }

    @Override
    public Map<String, Object> getLocationByUserId(User user) throws LibException {

        Map<String, Object> rtv = null;
        List<User> u = userMapper.selectByCondition(user);
        User user1 = u.get(0);

        if (null != user1) {

            if (2 == user1.getType()) {
                throw new LibException("当前用户已被禁用!");
            } else {
                BookCase bookCase = bookCaseMapper.selectByUserId(user1);
                if (null != bookCase) {
                    rtv = new HashMap<>(2);
                    rtv.put("location", bookCase.getLocation());
                    rtv.put("number", bookCase.getNumber());
                } else {
                    throw new LibException("您没有可用柜子!");
                }
            }
        }
        return rtv;
    }

    @Override
    public boolean putShip(ShipVo shipVO) throws LibException {
        if (null == shipVO.getStudentId()) {
            shipVO.setStatus(0);
        } else {
            shipVO.setStatus(1);
            User user = bookCaseMapper.selectUserIdByStudentId(shipVO);
            BookCase bookCase = bookCaseMapper.selectByNumber(shipVO);
            if (null == bookCase) {
                throw new LibException("此书包柜不存在");
            }
            if (null != user) {
                shipVO.setUserId(user.getSystemId());
            }
        }
        int i = bookCaseMapper.updateSingleShip(shipVO);
        if (0 == i) {
            throw new LibException("修改失败");
        } else {
            redisDao.updateRedis(); // 同步MySQL数据库
            return true;
        }


    }

    @Override
    public RestData setKeepByNumber(List<Integer> data) {
        int success = 0;
        if (0 == data.size() || null == data) {
            return new RestData(1, "无预留柜子编号!");
        } else {
            for (int count = 0; count < data.size(); count++) {
                ShipVo shipVo = new ShipVo();
                shipVo.setNumber(data.get(count).toString());
                BookCase bookCase = bookCaseMapper.selectByNumber(shipVo);
                if (null != bookCase) {
                    if (null != bookCase.getUserId()){
                        return new RestData(1, "柜子存在已被占用的情况!");
                    }
                } else {
                    return new RestData(1, "柜子编号有误!");
                }
            }

            for (int count = 0; count < data.size(); count++) {
                BookCase bookCase = new BookCase();
                bookCase.setNumber(data.get(count));
                if (0 >= bookCaseMapper.setByNumber(bookCase)) {
                    success = 1;
                    break;
                }
            }
            if (0 == success) {
                redisDao.updateRedis();
                return new RestData(0, "操作成功!");
            } else {
                return new RestData(1, "操作失败,请重试!");
            }
        }
    }

    @Override
    public RestData deleteShip(List<Integer> data) {
        int success = 0;
        if (0 == data.size() || null == data) {
            if (0 >= bookCaseMapper.deleteAllShip()) {
                return new RestData(1, "柜子已全部清空关系!");
            } else {
                return new RestData(0, "操作成功!");
            }
        } else {
            for (int count = 0; count < data.size(); count++) {
                BookCase bookCase = new BookCase();
                bookCase.setNumber(data.get(count));
                if (0 >= bookCaseMapper.deleteShipByNumber(bookCase)) {
                    success = 1;
                    break;
                }
            }
            if (0 == success) {
                redisDao.updateRedis();
                return new RestData(0, "操作成功!");
            } else {
                return new RestData(1, "操作失败,请重试!");
            }
        }
    }

    @Override
    public List<Map<String, Object>> getBagNum() {
        List<Map<String, Object>> rtv = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Map<String, Object> map = new HashMap<>(2);
            int num = Integer.parseInt(redisDao.get("location_" + i));
            map.put("location", i);
            map.put("num", num);
            rtv.add(map);
        }
        return rtv;
    }

    @Override
    public String processParameter(BookCaseVo bookCaseVo) {

        /*对书包柜编号范围Id分割*/
        if (null != bookCaseVo.getId() && 0 != bookCaseVo.getId().length()) {
            String id = bookCaseVo.getId();
            if (id.indexOf("-") != -1) {
                String[] splitstr = id.split("-");
                bookCaseVo.setL(splitstr[0]);
                bookCaseVo.setR(splitstr[1]);
            } else {
                bookCaseVo.setL(id);
                bookCaseVo.setR(id);
            }
        }

        /*根据学号查找与书包柜关联的userId*/
        String mes = null;
        if (null != bookCaseVo.getStudentId() && 0 != bookCaseVo.getStudentId().length()) {
            User user = new User();
            user.setStudentId(bookCaseVo.getStudentId());
            List<User> users = userMapper.selectByCondition(user);
            if (0 < users.size()) {
                User u = users.get(0);
                bookCaseVo.setUserId(u.getSystemId());
            } else {
                mes = "请确认学号是否有误";
            }
        }
        return mes;
    }

    @Override
    public RestData encapsulate(List<BookCase> bookCases, BookCaseVo bookCaseVo, Page page) {

        List<Map<String, Object>> rtv = new ArrayList<>();
        for (BookCase data : bookCases) {
            Map<String, Object> map = new HashMap<>(4);
            map.put("location", data.getLocation());
            map.put("id", data.getNumber());
            map.put("status", data.getStatus());
            Integer userId = data.getUserId();
            if (null != userId) {
                User user = userMapper.selectByUserId(data);
                map.put("studentId", user.getStudentId());
            } else {
                map.put("studentId", "");
            }
            rtv.add(map);
        }
        Map<String, Object> map = new HashMap(2);
        map.put("ships", rtv);
        map.put("pages", page);
        return new RestData(map);
    }

    @Override
    public RestData selectDetailByCondition(BookCaseVo bookCaseVo) {

        if (null != bookCaseVo.getStatus() && 2 == bookCaseVo.getStatus() && null != bookCaseVo.getStudentId()) {
            return new RestData(1, "无效查询");
        }
        if (null != bookCaseVo.getStatus() && 0 == bookCaseVo.getStatus() && null != bookCaseVo.getStudentId()) {
            return new RestData(1, "无效查询");
        }
        String message = processParameter(bookCaseVo);
        /*传入的学号有误*/
        if (null != message) {
            return new RestData(1, message);
        }

        Page page;
        if (null == bookCaseVo.getPage()) {
            bookCaseVo.setPage(1);
        }
        if (null == bookCaseVo.getSystemId()) {
            page = bookCaseMapper.countByCondition(bookCaseVo);
            page.setNowPage(bookCaseVo.getPage());
            page = PageUtil.checkPage(page);
            List<BookCase> bookCases = bookCaseMapper.selectDetailByCondition(bookCaseVo, page);
            return encapsulate(bookCases, bookCaseVo, page);
        }

        page = bookCaseMapper.countByCondition(bookCaseVo);
        page.setNowPage(bookCaseVo.getPage());
        page = PageUtil.checkPage(page);
        List<BookCase> bookCases = bookCaseMapper.selectDetailByCondition(bookCaseVo, page);
        return encapsulate(bookCases, bookCaseVo, page);

    }

    @Override
    public Boolean postBoxOrder(BookCaseVo bookCaseVo) {
        Boolean rtv = false;
        String key = bookCaseVo.getStudentId();
        String location = bookCaseVo.getLocation().toString();
        long count = redisDao.getListSize("userQueue");
        redisDao.set("l_" + key, location + "," + count);
        int total = Integer.parseInt(redisDao.get("total"));

        /**
         * 1. 进来先查 location 是否还有柜子
         * 2. 如果有，判断 c_ 大于一 入队 不大于一 total--
         * 3. 如果没有柜子返回false
         */

        if (total > 0 && Integer.parseInt(redisDao.get("location_" + location)) > 0) {
            if (redisDao.inc("c_" + key, 1) > 1) {
                redisDao.pushValue("userQueue", key);
            } else {
                redisDao.pushValue("userQueue", key);
                redisDao.dec("total", 1);
            }
            rtv = true;
        }
        return rtv;

    }

    int maxLocation() {
        int maxValue = Integer.parseInt(redisDao.get("location_1"));
        int maxL = 1;

        for (int i = 2; i <= 4; i++) {
            int nowValue = Integer.parseInt(redisDao.get("location" + i));
            if (nowValue > maxValue) {
                maxValue = nowValue;
                maxL = i;
            }
        }

        return maxL;
    }

    @Override
    public void boxQueue(String studentId) {

        // 当前是否是第一次排队，如果不是不处理
        if (redisDao.dec("c_" + studentId, 1) == 0) {
            logger.info("====当前处理{}", studentId);
            int l = Integer.parseInt(redisDao.get("l_" + studentId).split(",")[0]);
            // 如果当前区域没有柜子
            if ("0".equals(redisDao.get("location_" + l))) {
                // 取当前区域中的最大值
                l = maxLocation();
            }
            // 不包含在finish中就分配柜子
            if (!redisDao.isMember("finish", studentId)) {
                BookCase bookCase = bookCaseMapper.selectBookCaseNumberByLocation(l);
                User user = new User();
                user.setStudentId(studentId);
                List<User> reUser = userMapper.selectByCondition(user);
                bookCaseMapper.updateOwnerbyBcNumber(bookCase.getNumber(), reUser.get(0).getSystemId());
                redisDao.dec("location_" + l, 1);
                redisDao.add("finish", studentId);
            } else {
                logger.info(studentId + "已经分配柜子，存在刷柜子嫌疑");
            }
        }
    }

    @Override
    public String popQueue() {
        String studentId = redisDao.popValue("userQueue");

        if (studentId != null) {
            redisDao.inc("popCount", 1);
        }

        return studentId;
    }

}

