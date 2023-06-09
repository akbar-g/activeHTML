package com.pidan.job.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pidan.model.entity.User;
import com.pidan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.pidan.constant.UserConstant.PREFIX_KEY;

/**
 * @author 黄大头
 * @date 2023年04月19日 20:14
 */
//@Component
@Slf4j
public class PreCacheJob {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 重点用户
    private List<Long> mainUserList = Arrays.asList(1L);

    // 每天执行，预热推荐用户
    @Scheduled(cron = "0/2 * * * * ?")   //自己设置时间测试
    public void doCacheRecommendUser() {
        //查数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> userPage = userService.page(new Page<>(1,20),queryWrapper);
        String redisKey = String.format(PREFIX_KEY,mainUserList);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //写缓存,20s过期
        try {
            valueOperations.set(redisKey,userPage,20000, TimeUnit.MILLISECONDS);
        } catch (Exception e){
            log.error("redis set key error",e);
        }
    }
}
