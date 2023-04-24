package com.pidan.model.request.teamrequest;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 黄大头
 * @date 2023年04月24日 16:22
 * 用户更新请求体
 */
@Data
public class TeamUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = -682147837689083348L;
}
