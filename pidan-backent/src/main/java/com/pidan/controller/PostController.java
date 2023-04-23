package com.pidan.controller;

import com.google.gson.Gson;
import com.pidan.common.BaseResponse;
import com.pidan.common.ErrorCode;
import com.pidan.common.ResultUtils;
import com.pidan.model.entity.Posts;
import com.pidan.model.entity.User;
import com.pidan.model.request.postrequest.PostAddRequest;
import com.pidan.model.request.postrequest.PostDeleteRequest;
import com.pidan.model.request.postrequest.PostsEditRequest;
import com.pidan.exception.BusinessException;
import com.pidan.exception.ThrowUtils;
import com.pidan.service.PostsService;
import com.pidan.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 新闻
 * @author 黄大头
 * @date 2023年03月25日 21:26
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostsService postsService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();
    /**
     * 创建
     *
     * @param postAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addPost(@RequestBody PostAddRequest postAddRequest, HttpServletRequest request) {
        if (postAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Posts posts = new Posts();
        BeanUtils.copyProperties(postAddRequest, posts);
        List<String> tags = postAddRequest.getTags();
        if (tags != null) {
            posts.setTags(GSON.toJson(tags));
        }
        postsService.validPost(posts, true);
        User loginUser = userService.getLoginUser(request);
        posts.setUserId(loginUser.getId());
        boolean result = postsService.save(posts);
        if (!result){
            throw new BusinessException(ErrorCode.INSERT_ERROR);
        }
        long newPostId = posts.getId();
        return ResultUtils.success(newPostId);
    }


    /**
     * 删除
     *
     * @param postDeleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody PostDeleteRequest postDeleteRequest, HttpServletRequest request) {
        if (postDeleteRequest == null || postDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = postDeleteRequest.getId();
        // 判断是否存在
        Posts oldPost = postsService.getById(id);
        if (oldPost==null){
            throw new BusinessException(ErrorCode.NOT_DATA);
        }
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        boolean b = postsService    .removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 编辑（用户）
     *
     * @param postEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editPost(@RequestBody PostsEditRequest postEditRequest, HttpServletRequest request) {
        if (postEditRequest == null || postEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Posts post = new Posts();
        BeanUtils.copyProperties(postEditRequest, post);
        List<String> tags = postEditRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        // 参数校验
        postsService.validPost(post, false);
        User loginUser = userService.getLoginUser(request);
        long id = postEditRequest.getId();
        // 判断是否存在
        Posts oldPost = postsService.getById(id);

        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_DATA);
        // 仅本人或管理员可编辑
        if (!oldPost.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        boolean result = postsService.updateById(post);
        return ResultUtils.success(result);

    }




}
