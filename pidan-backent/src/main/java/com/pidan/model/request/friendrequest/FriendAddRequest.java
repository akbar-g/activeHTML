package com.pidan.model.request.friendrequest;

import lombok.Data;

import java.io.Serializable;

/** 添加好友请求
 *
 * @author 黄大头
 * @date 2023年04月25日 9:42
 */
@Data
public class FriendAddRequest implements Serializable {
    /**
     * userId
     */
    private Long userId;
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 备注信息
     */
    private String remark;

    private static final long serialVersionUID = 4815430069854991061L;
}
