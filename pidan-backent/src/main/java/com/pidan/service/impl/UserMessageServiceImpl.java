package com.pidan.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pidan.mapper.UserMessageMapper;
import com.pidan.model.entity.UserMessage;
import com.pidan.service.UserMessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
* @author 73782
* @description 针对表【user_message(聊天记录表)】的数据库操作Service实现
* @createDate 2023-04-21 15:33:53
*/
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage>
    implements UserMessageService {



    @Override
    public List<UserMessage> getMessageRecords(String username, String toName) {
        List userMessageArrayList = new ArrayList();
        QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fromName",username);
        queryWrapper.eq("toName",toName);
        List<UserMessage> list = this.list(queryWrapper);
        userMessageArrayList.add(list);
        QueryWrapper<UserMessage> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("fromName",toName);
        queryWrapper1.eq("toName",username);
        List<UserMessage> list1 = this.list(queryWrapper1);
        userMessageArrayList.add(list1);
        return userMessageArrayList;
    }

    @Override
    public Boolean DeleteExpireTimeRecords() {
        //todo 查询七天前数据？
        // 获取
        QueryWrapper<UserMessage> userMessageQueryWrapper = new QueryWrapper<>();
        //获取当前时间
        Date date = new Date();
        //userMessageQueryWrapper.gt("updateTime",date);
        userMessageQueryWrapper.lt("updateTime",date);

        List<UserMessage> list = list(userMessageQueryWrapper);

        System.out.println("list = " + list);
        return null;
    }




}




