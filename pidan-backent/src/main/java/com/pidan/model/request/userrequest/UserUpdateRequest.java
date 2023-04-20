package com.pidan.model.request.userrequest;

import lombok.Data;

import java.util.Date;

/**
 * @author 黄大头
 * @date 2023年04月01日 11:53
 */
@Data
public class UserUpdateRequest {

    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;


    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;


    private Date updateTime;

    private String tags;


    private Integer isDelete;
}
