package com.pidan.model.request.friendrequest;

import lombok.Data;

import java.io.Serializable;

/** 添加好友请求
 *
 * @author 黄大头
 * @date 2023年04月25日 9:42
 */
@Data
public class FriendQuery implements Serializable {
    private static final long serialVersionUID = 2185069766658726261L;
    /**
     * userId
     */
    private Long userId;


}
