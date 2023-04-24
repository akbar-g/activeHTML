package com.pidan.model.request.teamrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄大头
 * @date 2023年04月24日 17:02
 * <p>
 * 用户加入队伍请求体
 */
@Data
public class TeamJoinRequest implements Serializable {

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
    private static final long serialVersionUID = 8257144526264494847L;
}
