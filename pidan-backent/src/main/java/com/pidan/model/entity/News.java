package com.pidan.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 新闻表
 * @TableName news
 */
@TableName(value ="news")
@Data
public class News implements Serializable {
    /**
     * 新闻id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻url
     */
    private String herf;

    /**
     * 发布时间
     */
    private String publishTime;

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