package com.pidan.service;

import com.pidan.model.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 73782
* @description 针对表【posts(新闻表)】的数据库操作Service
* @createDate 2023-04-04 14:55:58
*/
public interface PostsService extends IService<Posts> {

    /**
     * 校验
     *
     * @param posts
     * @param add
     */
    void validPost(Posts posts, boolean add);

    /**
     * 获取帖子封装
     *
     * @param post
     * @param request
     * @return
     */
    //PostVO getPostVO(Posts post, HttpServletRequest request);
}
