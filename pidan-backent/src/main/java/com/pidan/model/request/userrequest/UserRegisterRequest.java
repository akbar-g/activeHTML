package com.pidan.model.request.userrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册参数
 * @author 黄大头
 * @date 2023年03月25日 21:31
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String phone;

    private String checkPassword;
}
