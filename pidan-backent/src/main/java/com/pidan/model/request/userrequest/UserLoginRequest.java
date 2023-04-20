package com.pidan.model.request.userrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 * @author 黄大头
 * @date 2023年03月25日 21:46
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
