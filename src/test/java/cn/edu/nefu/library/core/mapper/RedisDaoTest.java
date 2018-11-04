package cn.edu.nefu.library.core.mapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : Hunter
 * @date : 18-10-31 上午9:14
 * @since : Java 8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Before
    public void setUp() {
        Assert.assertNotNull(redisDao);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getList() {
    }

    @Test
    public void getListSize() {
    }

    @Test
    public void pushValue() {
    }

    @Test
    public void pushList() {
    }

    @Test
    public void popValue() {
    }

    @Test
    public void removeListValue() {
    }

    @Test
    public void dec() {
    }

    @Test
    public void set() {
    }

    @Test
    public void get() {
    }
}