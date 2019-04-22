/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper.redis;

import cn.itgardener.nefu.library.common.util.JsonUtil;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jimi
 * @date : 2018/10/30
 * @since : Java 8
 */
@Repository
public class RedisDaoImpl implements RedisDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StringRedisTemplate stringRedisTemplate;

    public RedisDaoImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
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
    public boolean listRightPush(String key, String value) {
        Long result = stringRedisTemplate.opsForList().rightPush(key, value);
        return null != result && 0 >= result;
    }

    @Override
    public String listLiftPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    @Override
    public Long stringDecr(String key) {
        return stringRedisTemplate.opsForValue().decrement(key);
    }

    @Override
    public boolean stringIncr(String key) {
        Long result = stringRedisTemplate.opsForValue().increment(key);
        return null != result;
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
    public void putHash(String hashName, String key, String value) {
        stringRedisTemplate.opsForHash().put(hashName, key, value);
    }

    @Override
    public String getHash(String hashName, String key) {
        String rtv = null;

        Object object = stringRedisTemplate.opsForHash().get(hashName, key);
        if (null != object) {
            rtv = object.toString();
        }

        return rtv;
    }

    @Override
    public boolean removeAllKey() {
        Long delete = stringRedisTemplate.delete(stringRedisTemplate.keys("*"));
        return delete > 0;
    }

    @Override
    public List<Object> addAndSize(String setName, String value) {

        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                stringRedisTemplate.opsForSet().add(setName, value);
                stringRedisTemplate.opsForSet().size(setName);
                return redisOperations.exec();
            }
        };

        Object object = stringRedisTemplate.execute(sessionCallback);
        if (null == object || 1 > object.toString().length()) {
            return new ArrayList<>();
        }

        return JsonUtil.getListFromJson(object.toString());
    }

    @Override
    public void setRemove(String setName, String value) {
        stringRedisTemplate.opsForSet().remove(setName, value);
    }

    @Override
    public void putMap(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getMap(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
