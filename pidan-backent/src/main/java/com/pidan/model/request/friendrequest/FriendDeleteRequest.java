package com.pidan.model.request.friendrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除好友请求参数
 *
 * @author 黄大头
 * @date 2023年04月25日 13:59
 */
@Data
public class FriendDeleteRequest  implements Serializable {
    /**
     * userId
     */
    private Long userId;

    private static final long serialVersionUID = -3247078448101939063L;
}
