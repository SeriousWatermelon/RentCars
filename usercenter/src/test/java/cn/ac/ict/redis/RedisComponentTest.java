package cn.ac.ict.redis;

import cn.ac.ict.component.RedisComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisComponentTest {

    @Resource
    private RedisComponent redisComponent;

    @Test
    public void get() {
        String key = "redis-key";
        String value = redisComponent.get(key);
        System.out.println(value);
    }

    @Test
    public void mGet() {
        List<String> result = redisComponent.mGet("redis-key", "redis-key2");
        result.forEach(System.out::println);
    }

    @Test
    public void set() {
        String key = "redis-key";
        String value = "redis-value";
        Boolean result = redisComponent.set(key, value, 100);
        System.out.println(result);
    }

    @Test
    public void exists() {
    }

    @Test
    public void del() {
    }

    @Test
    public void incr() {
    }

    @Test
    public void getAllKey() {
    }

    @Test
    public void expire() {
    }
}
