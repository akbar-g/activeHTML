package com.pidan.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天记录表
 *
 * @TableName user_message
 */
@TableName(value = "user_message")
@Data
public class UserMessage implements Serializable {
    /**
     * message_id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送人
     */
    private String fromName;

    /**
     * 消息
     */
    private String message;

    /**
     * 接收人
     */
    private String toName;

    /**
     *
     */
    private  Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}