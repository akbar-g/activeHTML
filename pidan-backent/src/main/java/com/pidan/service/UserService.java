package com.pidan.service;

import com.pidan.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pidan.model.request.userrequest.UserPerfectRequest;
import com.pidan.model.request.userrequest.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 73782
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-03-25 21:10:42
*/
public interface UserService extends IService<User> {

    long userRegister(String userAccount, String userPassword, String checkPassword,String phone);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    int userLogout(HttpServletRequest request);

    User searchUser(long id);

    List<User> searchUserList(String userName);

    List<User> searchUserByTags(List<String> tagNameList);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);
    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    User getSafetyUser(User originUser);

   // boolean updateUser(UserPerfectRequest userPerfectRequest,HttpServletRequest request);

    int updateUser(User user,String newPassword, User loginUser);


}

