package com.pidan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pidan.model.entity.UserMessage;

import java.util.List;

/**
* @author 73782
* @description 针对表【user_message(聊天记录表)】的数据库操作Service
* @createDate 2023-04-21 15:33:53
*/

public interface UserMessageService extends IService<UserMessage> {

    List<UserMessage> getMessageRecords(String username, String toName);

    Boolean DeleteExpireTimeRecords();


}
