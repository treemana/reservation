/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.impl;

import cn.itgardener.nefu.library.common.GlobalConst;
import cn.itgardener.nefu.library.core.mapper.BookCaseMapper;
import cn.itgardener.nefu.library.core.mapper.ConfigMapper;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.BookCase;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.LocationVo;
import cn.itgardener.nefu.library.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Hunter
 * @date : 19-4-15 下午3:42
 * @since : Java 8
 */
@Service
public class RedisServiceImpl implements RedisService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookCaseMapper bookCaseMapper;
    private final ConfigMapper configMapper;
    private final RedisDao redisDao;
    private final UserMapper userMapper;

    public RedisServiceImpl(BookCaseMapper bookCaseMapper, ConfigMapper configMapper, RedisDao redisDao, UserMapper userMapper) {
        this.bookCaseMapper = bookCaseMapper;
        this.configMapper = configMapper;
        this.redisDao = redisDao;
        this.userMapper = userMapper;
    }

    @Override
    public boolean updateRedis() {
        try {
            int count = 0;
            LocationVo locationVo = new LocationVo();
            for (int i = 1; i <= 6; i++) {
                locationVo.setFloor(i);
                List<Config> list = configMapper.selectFloorLocation(locationVo);
                redisDao.set("floor_" + i, String.valueOf(list.size()));
                for (int j = 1; j <= list.size(); j++) {
                    if ("0".equals(configMapper.selectLocation(i + "_" + j).get(0).getConfigValue())) {
                        redisDao.set("location_" + i + "_" + j, "-1");
                        continue;
                    }
                    int num = bookCaseMapper.selectBagNum(i + "_" + j);
                    count += num;
                    redisDao.set("location_" + i + "_" + j, String.valueOf(num));
                }
            }
            redisDao.set("popCount", "0");
            redisDao.set("total", String.valueOf(count));
            Config configOpenTime = configMapper.selectStartTime();
            Config configEndTime = configMapper.selectEndTime();
            redisDao.set("openTime", configOpenTime.getConfigValue());
            redisDao.set("endTime", configEndTime.getConfigValue());
            List<BookCase> bookCases = bookCaseMapper.selectBookcase();
            for (BookCase bookcase : bookCases) {
                User user = new User();
                user.setSystemId(bookcase.getUserId());
                List<User> users = userMapper.selectByCondition(user);
                redisDao.add("finish", users.get(0).getStudentId());
            }

            return true;
        } catch (Exception e) {
            logger.info("updateRedis" + e);
            return false;
        }
    }

    @Override
    public int getTotal() {
        return Integer.parseInt(redisDao.get(GlobalConst.TOTAL));
    }

    @Override
    public boolean pushQueue(String studentId) {
        return redisDao.listRightPush(GlobalConst.QUEUE_LIST, studentId);
    }

    @Override
    public String popQueue() {
        return redisDao.listLiftPop(GlobalConst.QUEUE_LIST);
    }

    @Override
    public List<Integer> addAndSize(String studentId) {
        List<Integer> rtv = new ArrayList<>();

        List<Object> arr = redisDao.addAndSize(GlobalConst.ALL_RESERVATION_SET, studentId);

        for (Object obj : arr) {
            rtv.add(Integer.parseInt(obj.toString()));
        }

        return rtv;
    }

    @Override
    public void totalSetRemove(String studentId) {
        redisDao.setRemove(GlobalConst.ALL_RESERVATION_SET, studentId);
    }

    @Override
    public void addLocation(String studentId, String location) {
        redisDao.putHash(GlobalConst.LOCATION_HASH, studentId, location);
    }

    @Override
    public String getLocation(String studentId) {
        return redisDao.getHash(GlobalConst.LOCATION_HASH, studentId);
    }

    @Override
    public int decrLocation(String location) {
        int rtv = -1;
        Long result = redisDao.stringDecr(GlobalConst.LOCATION_PREFIX + location);
        if (null != result) {
            rtv = Math.toIntExact(result);
        }
        return rtv;
    }

    @Override
    public boolean incrLocation(String location) {
        return redisDao.stringIncr(GlobalConst.LOCATION_PREFIX + location);
    }

    @Override
    public void putCaptcha(String studentId, String captcha) {
        redisDao.putHash(GlobalConst.CAPTCHA_HASH, studentId, captcha);
    }

    @Override
    public String getCaptcha(String studentId) {
        return redisDao.getHash(GlobalConst.CAPTCHA_HASH, studentId);
    }
}
