package com.pidan.model.request.postrequest;

import lombok.Data;

import java.util.List;

/**
 * @author 黄大头
 * @date 2023年04月04日 17:12
 */
@Data
public class PostsEditRequest {
    /**
     * 帖子id
     */
    private Long id;


    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;


    /**
     * 帖子标签
     */
    private List<String> tags;


    /**
     * 帖子封面图片url json
     */
    private String titlePicHref;
}

