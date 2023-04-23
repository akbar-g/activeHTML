package com.pidan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pidan.common.BaseResponse;
import com.pidan.common.ErrorCode;
import com.pidan.common.ResultUtils;
import com.pidan.constant.UserConstant;
import com.pidan.model.entity.User;
import com.pidan.model.request.userrequest.UserLoginRequest;
import com.pidan.model.request.userrequest.UserRegisterRequest;
import com.pidan.model.vo.UserVO;
import com.pidan.exception.BusinessException;
import com.pidan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.pidan.constant.UserConstant.PREFIX_KEY;

/**
 * @author 黄大头
 * @date 2023年03月25日 21:26
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String phone = userRegisterRequest.getPhone();
        if (StringUtils.isAnyEmpty(userAccount, userPassword, checkPassword, phone)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, phone);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyEmpty(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 注销
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(long id, HttpServletRequest request) {

        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 查询用户
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<User> searchUser(long id, HttpServletRequest request) {

        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User user = userService.searchUser(id);
        return ResultUtils.success(user);
    }

    /**
     * 查询用户列表(仅管理员)
     *
     * @param userName
     * @param request
     * @return
     */
    @GetMapping("/searchList")
    public BaseResponse<List<User>> searchUserList(String userName, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        List<User> userList = userService.searchUserList(userName);
        return ResultUtils.success(userList);
    }


    /**
     * 主页列表
     * @param request
     * @return
     */
    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommendUser(long pageSize, long pageNum, HttpServletRequest request){
            User loginUser = userService.getLoginUser(request);
        String PrefexKey =String.format(PREFIX_KEY,loginUser.getId());

        String redisKey = PrefexKey +  pageNum+"";
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //如果有缓存，直接读取
        Page<User> userPage = (Page<User>) valueOperations.get(redisKey);
        if (userPage !=null){
            return ResultUtils.success(userPage);
        }
        //无缓存，查数据库
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userPage= userService.page(new Page<>(pageSize, pageSize), userQueryWrapper);
        //写缓存。20s过期
        try {
            valueOperations.set(redisKey,userPage,5, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("redis set key error",e);
        }
        //List<User> list  = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(userPage);

//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        Page<User> userList = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        return ResultUtils.success(userList);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }


    /**
     * 根据标签查询用户
     *
     * @param tageNameList
     * @return
     */
    @PostMapping("/selectUserByTag")
    public BaseResponse<List<User>> selectUserByTag(List<String> tageNameList) {

        if (CollectionUtils.isEmpty(tageNameList)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "查找标签不能为空");
        }
        List<User> userList = userService.searchUserByTags(tageNameList);
        return ResultUtils.success(userList);
    }


    /**
     * 更新或完善用户信息
     *
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Integer> update(@RequestBody User user,String newPassword, HttpServletRequest request) {
        //1.校验参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        //鉴权
        User loginUser = userService.getLoginUser(request);
        //boolean result = userService.updateUser(userPerfectRequest, request);
        int result = userService.updateUser(user,newPassword, loginUser);
        return ResultUtils.success(result);
    }


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }

}
