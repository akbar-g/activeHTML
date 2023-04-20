package com.pidan.model.request.userrequest;


import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄大头
 * @date 2023年04月02日 17:57
 */
@Data
public class UserPerfectRequest implements Serializable {
    private static final long serialVersionUID = -7918762635111292024L;

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
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;



    /**
     * 用户标签
     */
    private String tags;
}
