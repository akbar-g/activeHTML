package com.pidan.job.deleteMessage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pidan.model.entity.User;
import com.pidan.model.entity.UserMessage;
import com.pidan.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.pidan.constant.UserConstant.PREFIX_KEY;

/**
 * @author 黄大头
 * @date 2023年04月22日 16:18
 */
@Slf4j
//@Component
public class DeleteMessages {

    @Resource
    private UserMessageService userMessageService;

    @Scheduled(cron = "0 0 0 */7 * ? *")//设置每七天执行一次
    public void doCacheRecommendUser() {
        Boolean result = userMessageService.DeleteExpireTimeRecords();

    }


}
