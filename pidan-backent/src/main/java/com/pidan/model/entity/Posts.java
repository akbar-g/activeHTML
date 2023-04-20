package com.pidan.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 新闻表
 * @TableName posts
 */
@TableName(value ="posts")
@Data
public class Posts implements Serializable {
    /**
     * 帖子id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 标签列表 json
     */
    private String tags;

    /**
     * 帖子封面图片url json
     */
    private String titlePicHref;


    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}