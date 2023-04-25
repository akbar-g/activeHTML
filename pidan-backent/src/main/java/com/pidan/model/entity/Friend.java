package com.pidan.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 好友关系表
 * @TableName friend
 */
@TableName(value ="friend")
@Data
public class Friend implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送方用户Id
     */
    private Long fromUserId;

    /**
     * 接收方用户Id
     */
    private Long toUserId;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 拉黑状态 0 - 正常, 1-拉黑
     */
    private Integer friendStatus;

    /**
     * 删除状态 0 - 正常, 1-删除
     * 想了很久 还是决定先直接删掉，简单
     */
    //@TableLogic
    private Integer friendBlack;


    /**
     * 是否接受好友请求，0-未接受，1-已接受
     */
    private Integer accepted;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}