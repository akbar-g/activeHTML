package com.pidan.constant;

/**
 * 用户常量
 *
 * @author pidan
 */
public interface UserConstant {
    /**
     * 盐值，混淆密码
     */
     static final String SALT = "pidan";

    /**
     * Redis前缀
     */
    static final String PREFIX_KEY = "pidan:user:recommend:%s";

    static final String REDISSION_LOCK = "pidan:user:";

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    //  ------- 权限 --------

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;

}
