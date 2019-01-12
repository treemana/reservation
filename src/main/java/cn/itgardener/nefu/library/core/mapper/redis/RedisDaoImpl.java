/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper.redis;

import cn.itgardener.nefu.library.core.mapper.BookCaseMapper;
import cn.itgardener.nefu.library.core.mapper.ConfigMapper;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.vo.LocationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author : Jimi
 * @date : 2018/10/30
 * @since : Java 8
 */
@Repository
public class RedisDaoImpl implements RedisDao {

    private final StringRedisTemplate stringRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookCaseMapper bookCaseMapper;
    private final ConfigMapper configMapper;

    public RedisDaoImpl(StringRedisTemplate stringRedisTemplate, BookCaseMapper bookCaseMapper, ConfigMapper configMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.bookCaseMapper = bookCaseMapper;
        this.configMapper = configMapper;
    }


    @Override
    public List<String> getList(String key, long start, long end) {
        try {
            return stringRedisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            logger.info("getList" + e.getMessage());
            return null;
        }
    }

    @Override
    public Long getListSize(String key) {
        try {
            return stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.info("add" + e.getMessage());
            return null;
        }

    }

    @Override
    public boolean pushValue(String key, String value) {
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            logger.info("pushValue" + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean pushList(String key, List<String> list) {
        try {
            stringRedisTemplate.opsForList().rightPushAll(key, list);
            return true;
        } catch (Exception e) {
            logger.info("pushList" + e.getMessage());
            return false;
        }

    }

    @Override
    public String popValue(String key) {
        try {
            return stringRedisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            logger.info("popValue" + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean removeListValue(String key, String value) {
        try {
            stringRedisTemplate.opsForList().remove(key, 1, value);
            return true;
        } catch (Exception e) {
            logger.info("removeListValue" + e.getMessage());
            return false;
        }
    }

    @Override
    public long dec(String key, long number) {
        try {
            Long l = stringRedisTemplate.opsForValue().increment(key, -number);
            return l;
        } catch (Exception e) {
            logger.info("dec" + e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.info("set" + e.getMessage());
            return false;
        }
    }

    @Override
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.info("get" + e.getMessage());
            return null;
        }
    }

    @Override
    public long inc(String key, long number) {
        try {
            Long l = stringRedisTemplate.opsForValue().increment(key, number);
            return l;
        } catch (Exception e) {
            logger.info("inc" + e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean add(String key, String value) {
        try {
            stringRedisTemplate.opsForSet().add(key, value);
            return true;
        } catch (Exception e) {
            logger.info("add" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isMember(String key, String value) {
        Boolean rt = stringRedisTemplate.opsForSet().isMember(key, value);

        if (rt == null) {
            return false;
        } else {
            return rt;
        }

    }

    @Override
    public boolean remove(String key) {
        try {
            stringRedisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            logger.info("remove" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateRedis() {
        try {
            int count = 0;
            LocationVo locationVo = new LocationVo();
            for (int i = 1; i <= 6; i++) {
                locationVo.setFloor(i);
                List<Config> list = configMapper.selectFloorLocation(locationVo);
                this.set("floor_" + i, String.valueOf(list.size()));
                for (int j = 1; j <= list.size(); j++) {
                    if ("1".equals(configMapper.selectLocation(i + "_" + j).get(0).getConfigValue())) {
                        this.set("location_" + i + "_" + "j", "0");
                        continue;
                    }
                    int num = bookCaseMapper.selectBagNum(i + "_" + j);
                    count += num;
                    this.set("location_" + i + "_" + j, String.valueOf(num));
                }
            }
            this.set("popCount", "0");
            this.set("total", String.valueOf(count));
            this.remove("finish");
            Config configOpenTime = configMapper.selectStartTime();
            Config configEndTime = configMapper.selectEndTime();
            this.set("openTime", configOpenTime.getConfigValue());
            this.set("endTime", configEndTime.getConfigValue());
            return true;
        } catch (Exception e) {
            logger.info("updateRedis" + e);
            return false;
        }
    }

    @Override
    public void pushHash(String key, String filed, String value) {
        try {
            stringRedisTemplate.opsForHash().put(key, filed, value);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    @Override
    public String getHash(String key, String filed) {
        try {
            return (String) stringRedisTemplate.opsForHash().get(key, filed);
        } catch (Exception e) {
            logger.info("get" + e.getMessage());
            return null;
        }
    }

}
