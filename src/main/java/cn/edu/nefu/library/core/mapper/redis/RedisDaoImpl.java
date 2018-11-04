
package cn.edu.nefu.library.core.mapper.redis;

import cn.edu.nefu.library.core.mapper.RedisDao;
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


    @Autowired
    public RedisDaoImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public List<String> getList(String key, long start, long end) {
        try {
            return stringRedisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getListSize(String key) {
        try {
            return stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean pushValue(String key, String value) {
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean pushList(String key, List<String> list) {
        try {
            stringRedisTemplate.opsForList().rightPushAll(key, list);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public String popValue(String key) {
        try {
            return stringRedisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeListValue(String key, String value) {
        try {
            stringRedisTemplate.opsForList().remove(key, 1, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long dec(String key, long number) {
        try {
            Long l = stringRedisTemplate.opsForValue().increment(key, -number);
            return l;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public boolean set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public long inc(String key, long number) {
        try {
            Long l = stringRedisTemplate.opsForValue().increment(key, number);
            return l;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean add(String key, String value) {
        try {
            stringRedisTemplate.opsForSet().add(key,value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isMember(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }


}
