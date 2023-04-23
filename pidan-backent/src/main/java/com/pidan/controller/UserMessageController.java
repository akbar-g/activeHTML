package com.pidan.controller;

import com.pidan.model.entity.UserMessage;
import com.pidan.service.UserMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 黄大头
 * @date 2023年04月21日 20:20
 */
@RequestMapping("userMessage")
@RestController
public class UserMessageController {
    @Resource
    private UserMessageService userMessageService;

    //
    @GetMapping("/getMessage/{userName}/{toName}")
    public List<UserMessage> getUserMessageRecords(@PathVariable String userName, @PathVariable String toName){
        List<UserMessage> messageRecords = userMessageService.getMessageRecords(userName, toName);
        return messageRecords;
    }
}
