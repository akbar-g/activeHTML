package com.pidan;

import com.pidan.job.cache.PreCacheJob;
import com.pidan.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * @author 黄大头
 * @date 2023年04月19日 17:24
 */
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void testRedis(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        //增
        //valueOperations.set("pidan","大林");
        valueOperations.set("pidanage",10);
        valueOperations.set("pidansex",1);
        User user = new User();
        user.setId(1l);
        user.setUsername("大霖");
        valueOperations.set("pidanUser",user);

        //查
       // Object pidan = valueOperations.get("pidanUser");
       // Assertions.assertTrue("大林".equals((User) pidan));

        Object pidanage = valueOperations.get("pidanUser");
        System.out.println(pidanage);

        //删
        redisTemplate.delete("pidan");
    }

    @Resource
    private PreCacheJob preCacheJob;

    @Test
    void testRedisCache(){
        preCacheJob.doCacheRecommendUser();
    }
}
