package com.pidan.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 黄大头
 * @date 2023年04月04日 16:06
 */
@Data
public class PostsVO {
    /**
     * 帖子id
     */
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
     * 标签列表 json
     */
    private String tags;

    /**
     * 帖子封面图片url json
     */
    private String titlePicHref;

}
