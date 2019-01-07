/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.impl;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.TimeUtil;
import cn.itgardener.nefu.library.common.util.TokenUtil;
import cn.itgardener.nefu.library.common.util.VerifyUtil;
import cn.itgardener.nefu.library.core.mapper.BookCaseMapper;
import cn.itgardener.nefu.library.core.mapper.ConfigMapper;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.TimeVo;
import cn.itgardener.nefu.library.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : pc CMY
 * @date : 2018/10/30
 * @since : Java 8
 */
@Service
public class ReservationServiceImpl implements ReservationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConfigMapper configMapper;
    private final BookCaseMapper bookCaseMapper;
    private final RedisDao redisDao;
    private final UserMapper userMapper;

    @Autowired
    public ReservationServiceImpl(ConfigMapper configMapper, BookCaseMapper bookCaseMapper, RedisDao redisDao, UserMapper userMapper) {
        this.configMapper = configMapper;
        this.bookCaseMapper = bookCaseMapper;
        this.redisDao = redisDao;
        this.userMapper = userMapper;
    }

    @Override
    public boolean putReservationArea(List<Integer> list) throws LibException {
        int count = 0;
        for (int i = 1; i <= 4; i++) {
            Config config = new Config();
            config.setSystemId(i);
            if (list.contains(i)) {
                config.setConfigValue("0");
                count++;
            } else {
                config.setConfigValue("1");
            }
            configMapper.selectOpenAreaBySystemId(config);
        }
        if (count == list.size()) {
            redisDao.updateRedis();
            return true;
        } else {
            throw new LibException("修改失败");
        }
    }

    @Override
    public List<Map<String, String>> getReservationArea() throws LibException {

        List<Config> configs = configMapper.selectOpenArea();
        if (null == configs) {
            throw new LibException("预约区域为空");
        } else {

            List<Map<String, String>> rtv = new ArrayList<>(4);
            for (Config config : configs) {
                Map<String, String> map = new HashMap<>(3);
                if (config.getConfigKey().contains("area_")) {
                    map.put("systemId", config.getSystemId().toString());
                    map.put("configKey", config.getConfigKey());
                    map.put("configValue", config.getConfigValue());
                    rtv.add(map);
                }

            }
            return rtv;
        }
    }

    @Override
    public Map<String, String> getReservationTime() throws LibException {
        Map<String, String> rtv = new HashMap<>(2);
        List<Config> configs = configMapper.selectOpenTime();
        if (null == configs) {
            throw new LibException("查询失败");
        } else {
            rtv.put("startTime", redisDao.get("openTime"));
            for (Config config : configs) {
                if ("endTime".equals(config.getConfigKey())) {
                    rtv.put("endTime", config.getConfigValue());
                }
            }
        }
        return rtv;
    }

    @Override
    public RestData putReservationTime(TimeVo timeVO) {
        Config config = new Config();
        config.setConfigKey("startTime");
        config.setConfigValue(timeVO.getStartTime());
        Config config1 = new Config();
        config1.setConfigKey("endTime");
        config1.setConfigValue(timeVO.getEndTime());

        if (0 < configMapper.updateOpenTime(config) * configMapper.updateOpenTime(config1)) {
            redisDao.updateRedis();
            return new RestData(0, "修改成功");
        } else {
            return new RestData(1, "修改失败");
        }
    }

    @Override
    public Map<String, Object> getStartTime() {

        String nowTime = TimeUtil.getCurrentTime();

        Map<String, Object> rtv = new HashMap<>(2);
        rtv.put("startTime", redisDao.get("openTime"));
        rtv.put("nowTime", nowTime);
        return rtv;

    }

    @Override
    public List<HashMap<String, Object>> getAreaStatus(String studentId, String floor) throws ParseException {
        int locationNum = Integer.parseInt(redisDao.get("floor_" + floor));
        List<HashMap<String, Object>> rtv = new ArrayList<>(locationNum);

        //验证开放时间
        String nowTime = TimeUtil.getCurrentTime();
        String openTime = redisDao.get("openTime");
        String endTime = redisDao.get("endTime");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date nowDate = format.parse(nowTime);
        Date startDate = format.parse(openTime);
        Date endDate = format.parse(endTime);

        long now = nowDate.getTime();
        long start = startDate.getTime();
        long end = endDate.getTime();
        if (now < start || now > end) {
            for (int i = 1; i <= locationNum; i++) {
                HashMap<String, Object> hashMap = new HashMap<>(2);
                hashMap.put("location", floor + "_" + i);
                hashMap.put("status", 1);
                rtv.add(hashMap);
            }
            logger.info("不在开放时间");
            return rtv;
        }

        //判断是否已经分配了柜子
        if (redisDao.isMember("finish", studentId)) {
            for (int i = 1; i <= locationNum; i++) {
                HashMap<String, Object> hashMap = new HashMap<>(2);
                hashMap.put("location", floor + "_" + i);
                hashMap.put("status", 1);
                rtv.add(hashMap);
            }
            logger.info("已经分配了柜子");
            return rtv;
        }

        List<String> list = redisDao.getList("userQueue", 0, -1);
        //判斷排队队列中是否有该studentId
        if (list.contains(studentId)) {
            for (int i = 1; i <= locationNum; i++) {
                HashMap<String, Object> hashMap = new HashMap<>(2);
                hashMap.put("location", floor + "_" + i);
                hashMap.put("status", 1);
                rtv.add(hashMap);
            }
            logger.info("已在队列中");
            return rtv;
        }
        //联合判断 区域是否开放和柜子是否剩余
        for (int i = 1; i <= locationNum; i++) {
            if (Integer.parseInt(redisDao.get("location_" + floor + "_" + i)) > 0) {
                HashMap<String, Object> hashMap = new HashMap<>(2);
                hashMap.put("location", floor + "_" + i);
                hashMap.put("status", 0);
                rtv.add(hashMap);
            } else {
                logger.info("locatioin_" + floor + "_" + i + "区域未开放或该区域无剩余柜子");
                HashMap<String, Object> hashMap = new HashMap<>(2);
                hashMap.put("location", floor + "_" + i);
                hashMap.put("status", 1);
                rtv.add(hashMap);
            }
        }
        return rtv;
    }


    @Override
    public void verifyCode(String verifyCode, String studentId) throws LibException {
        String captchaId = redisDao.getHash("code", studentId);
        if (!captchaId.equals(verifyCode)) {
            throw new LibException("验证码出错!");
        }
        redisDao.pushHash("code", studentId, TokenUtil.getToken());

    }
}
