
package cn.edu.nefu.library.core.mapper.redis;

import cn.edu.nefu.library.core.mapper.RedisDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Logger logger;

    @Autowired
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
            stringRedisTemplate.opsForSet().add(key,value);
            return true;
        } catch (Exception e) {
            logger.info("add" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isMember(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
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


}
