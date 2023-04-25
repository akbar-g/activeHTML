package com.pidan.controller;

import com.pidan.common.BaseResponse;
import com.pidan.common.ErrorCode;
import com.pidan.common.ResultUtils;
import com.pidan.exception.BusinessException;
import com.pidan.model.entity.User;
import com.pidan.model.request.friendrequest.FriendAddRequest;
import com.pidan.model.request.friendrequest.FriendDeleteRequest;
import com.pidan.model.request.friendrequest.FriendQuery;
import com.pidan.model.vo.UserVO;
import com.pidan.service.FriendService;
import com.pidan.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 黄大头
 * @date 2023年04月25日 9:38
 */
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Resource
    private FriendService friendService;

    @Resource
    private UserService userService;

    /**
     * 申请添加好友
     *
     * @param request
     * @param friendAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addFriend(HttpServletRequest request, @RequestBody FriendAddRequest friendAddRequest) {
        if (friendAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = friendService.addFriend(loginUser, friendAddRequest);
        return ResultUtils.success(true);
    }

    /**
     * 接受好友申请
     * @param request
     * @param friendAddRequest
     * @return
     */
    @PostMapping("/accept")
    public BaseResponse<Boolean> acceptFriend(HttpServletRequest request, @RequestBody FriendAddRequest friendAddRequest) {
        if (friendAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = friendService.acceptFriedn(loginUser, friendAddRequest);
        return ResultUtils.success(true);
    }

    /**
     * 好友列表
     * @param request
     * @return
     */
    @GetMapping("/friendlist")
    public BaseResponse<List<UserVO>> friendList(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser ==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long loginUserId = loginUser.getId();
        List<UserVO> friendList = friendService.selectFriendList(loginUserId);
        return ResultUtils.success(friendList);
    }
    /**
     * 获取单个好友
     * @param request
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<UserVO> getFriend(HttpServletRequest request,  FriendQuery friendQuery) {
        User loginUser = userService.getLoginUser(request);
        if (friendQuery == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long loginUserId = loginUser.getId();

        UserVO userVO = friendService.getFriendOne(loginUserId,friendQuery);
        return ResultUtils.success(userVO);
    }


    /**
     * 删除好友
     * @param request
     * @param friendDeleteRequest
     * @return
     */
    @PostMapping("/deleteFriend")
    public BaseResponse<Boolean> deleteFriend(HttpServletRequest request, @RequestBody FriendDeleteRequest friendDeleteRequest){
        if (friendDeleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Long loginUserId = loginUser.getId();
        boolean result = friendService.deleteFriend(loginUserId,friendDeleteRequest);
        return ResultUtils.success(result);
    }



}
