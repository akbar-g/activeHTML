package com.pidan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pidan.common.ErrorCode;
import com.pidan.constant.UserConstant;
import com.pidan.model.entity.User;
import com.pidan.model.enums.UserRoleEnum;
import com.pidan.model.request.userrequest.UserPerfectRequest;
import com.pidan.exception.BusinessException;
import com.pidan.model.request.userrequest.UserUpdateRequest;
import com.pidan.service.UserService;
import com.pidan.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author 73782
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-03-25 21:10:42
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 盐值，混淆密码
     */
    //private static final String SALT = "pidan";

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String phone) {
        // 1. 校验
        if (StringUtils.isAnyEmpty(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        //校验两次密码是否相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        //todo 手机号码

        //账号不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long aLong = userMapper.selectCount(queryWrapper);
        if (aLong > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户已存在");
        }

        // 存的密码不能是明文
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPhone(phone);
        boolean save = this.save(user);
        if (!save) {
            return 400;
        }
        return user.getId();
    }


    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        // 1. 校验
        if (StringUtils.isAnyEmpty(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        //用户脱敏
        User safetyUser = getSafetyUser(user);
        //记录用户登录态信息
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 查询用户列表
     *
     * @param id
     * @return
     */
    @Override
    public User searchUser(long id) {
        if (id == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "请求参数为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id", id);
        User userUnsafe = this.getById(id);
        User safetyUser = getSafetyUser(userUnsafe);
        return safetyUser;
    }


    @Override
    public List<User> searchUserList(String userName) {
//        if (StringUtils.isEmpty(userName)){
//            throw new BusinessException(ErrorCode.NULL_ERROR, "请求参数为空");
//        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", userName);
        List<User> userList = list(queryWrapper);
        List<User> users = userList.stream().map(user -> getSafetyUser(user)).collect(Collectors.toList());
        return users;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }


    /**
     * 根据标签匹配用户 内存查询
     *
     * @param tagNameList
     * @return
     */
    @Override
    public List<User> searchUserByTags(List<String> tagNameList) {

        //校验
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 内存查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();
        //在内存中判断是否有匹配的标签
        return userList.stream().filter(user -> {
            String tagStr = user.getTags();
            if (StringUtils.isBlank(tagStr)) {
                return false;
            }
            Set<String> tempTagNameSet = gson.fromJson(tagStr, new TypeToken<Set<String>>() {
            }.getType());
            for (String tagName : tempTagNameSet) {
                if (!tempTagNameSet.contains(tagName)) {
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());

    }

    /**
     * 判断是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 查询当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Long userId = currentUser.getId();
        currentUser = getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return currentUser;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @param loginUser
     * @return
     */
    @Override
    public int updateUser(User user,String newPassword, User loginUser) {
        long userId = user.getId();
        if (userId <= 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //如果是管理员，允许更新任意用户
        //如果不是管理员，只允许更新自己的信息
        if (!isAdmin(loginUser) && userId != loginUser.getId()){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User userOld = userMapper.selectById(userId);
        if (userOld==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //校验密码
        //判断用户是否有密码
        String userPassword = user.getUserPassword();
        //原数据库密码
        String userPasswordOld = userOld.getUserPassword();
        //给用户输入的密码加密
        String entryPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());
        if (StringUtils.isNotEmpty(userPassword)){
            if (!entryPassword.equals(userPasswordOld)){
                throw new BusinessException(ErrorCode.PASSWORD_ERROR);
            }
        }

        String newPasswordSet = DigestUtils.md5DigestAsHex((UserConstant.SALT + newPassword).getBytes());
        user.setUserPassword(newPasswordSet);
        return userMapper.updateById(user);

    }



    /**
     * 根据标签查询用户 SQL查询
     *
     * @param tagNameList
     * @return
     */
    @Deprecated
    private List<User> searchUserByTagsBySQL(List<String> tagNameList) {
        //校验
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //SQL 查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //拼接 and 查询
        //like ‘%Java%’ and like ‘%Python%’
        for (String tagName : tagNameList) {
            queryWrapper.like("tags", tagName);
        }

        List<User> userList = userMapper.selectList(queryWrapper);
        //lambda表达式，也是java8的一个特性；就是如果说你要调用的这个函数，它接收的参数正好是你每次遍历的那个对象
        //userList.forEach(this::getSafetyUser);
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());

    }
}




