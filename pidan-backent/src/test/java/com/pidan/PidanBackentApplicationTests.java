package com.pidan;

import cn.hutool.core.date.DateTime;
import com.pidan.job.once.InsertUser;
import com.pidan.model.entity.User;
import com.pidan.model.request.userrequest.UserRegisterRequest;
import com.pidan.mapper.UserMapper;
import com.pidan.service.UserMessageService;
import com.pidan.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PidanBackentApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private InsertUser insertUser;

    @Resource
    private UserService userService;
    @Resource
    private UserMessageService userMessageService;

    @Test
    void contextLoads() {
    }
    @Test
    void testMapper() {
        User user = new User();
        user.setUsername("pidan");
        user.setUserAccount("pidan");
        user.setAvatarUrl("233323232");
        user.setGender(1);
        user.setUserPassword("12345678");
        user.setUserStatus(0);

        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    void testResgiter() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        String userAccount = "pidan";
        String userPassword = "12345678";

    }


    @Test
    void testSelectUserByTags() {
        List<String> tagNames = Arrays.asList("Java","Python","C++");
        List<User> userList = userService.searchUserByTags(tagNames);
        Assert.assertNotNull(userList);

    }


    @Test
    void testGetLoginUser() {
        List<String> tagNames = Arrays.asList("Java","Python","C++");
        List<User> userList = userService.searchUserByTags(tagNames);
        Assert.assertNotNull(userList);

    }



    @Test
    void deleteMessage(){
        Boolean aBoolean = userMessageService.DeleteExpireTimeRecords();
        System.out.println("aBoolean = " + aBoolean);
    }

//    @Test
//    void testInsertUser() {
//        insertUser.doConcurrencyInsertUser();
//    }




}
