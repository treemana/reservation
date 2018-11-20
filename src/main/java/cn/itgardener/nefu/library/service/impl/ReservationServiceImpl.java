/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.impl;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.core.mapper.BookCaseMapper;
import cn.itgardener.nefu.library.core.mapper.ConfigMapper;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.vo.GradeVo;
import cn.itgardener.nefu.library.core.model.vo.TimeVo;
import cn.itgardener.nefu.library.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    public ReservationServiceImpl(ConfigMapper configMapper, BookCaseMapper bookCaseMapper, RedisDao redisDao) {
        this.configMapper = configMapper;
        this.bookCaseMapper = bookCaseMapper;
        this.redisDao = redisDao;
    }

    @Override
    public Boolean putReservationArea(List<Integer> list) throws LibException {
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
            configMapper.updateOpenArea(config);
        }
        if (count == list.size()) {
            return true;
        } else {
            throw new LibException("修改失败");
        }

    }

    @Override
    public List<Map<String, String>> getReservationArea() throws LibException {

        List<Config> configs = configMapper.selectOpenAera();
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
    public boolean postGrade(GradeVo gradeVO) throws LibException {
        boolean rtv = false;
        Config config = new Config();
        config.setConfigKey("startGrade");
        config.setConfigValue(gradeVO.getStartGrade());
        int startGrade = configMapper.updateGrade(config);
        config.setConfigKey("endGrade");
        config.setConfigValue(gradeVO.getEndGrade());
        int endGrade = configMapper.updateGrade(config);
        if (0 == startGrade || 0 == endGrade) {
            throw new LibException("更新开放年级失败");
        } else {
            logger.info("更新成功");
            rtv = true;
        }
        return rtv;
    }

    @Override
    public Map<String, String> getReservationTime() throws LibException {
        Map<String, String> rtv = new HashMap<>(2);
        List<Config> configs = configMapper.selectOpenTime();
        if (null == configs) {
            throw new LibException("查询失败");
        } else {
            rtv.put("startTime" , redisDao.get("openTime"));
            for (Config config : configs) {
                if ("endTime".equals(config.getConfigKey())) {
                    rtv.put("endTime", config.getConfigValue());
                }
            }
        }

        return rtv;
    }

    @Override
    public boolean putReservationTime(TimeVo timeVO) {


        Config config = new Config();
        config.setConfigKey("startTime");
        config.setConfigValue(timeVO.getStartTime());
        Config config1 = new Config();
        config1.setConfigKey("endTime");
        config1.setConfigValue(timeVO.getEndTime());
        redisDao.updateRedis();
        return 0 < configMapper.updateOpenTime(config) * configMapper.updateOpenTime(config1);

    }

    @Override
    public Map<String, Object> getStartTime() {

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
        String nowTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> rtv = new HashMap<>(2);
        rtv.put("startTime", redisDao.get("openTime"));
        rtv.put("nowTime", nowTime);
        return rtv;

    }

    @Override
    public Map<String, Object> getOpenGrade() {
        Map<String, Object> rtv = new HashMap<>(2);
        Config config = new Config();
        config.setConfigKey("startGrade");
        Config c = configMapper.selectOpenGrade(config);
        rtv.put("startGrade", c.getConfigValue());
        config.setConfigKey("endGrade");
        c = configMapper.selectOpenGrade(config);
        rtv.put("endGrade", c.getConfigValue());
        return rtv;
    }
}
