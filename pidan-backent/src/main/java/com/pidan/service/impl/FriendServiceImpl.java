package com.pidan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pidan.common.ErrorCode;
import com.pidan.exception.BusinessException;
import com.pidan.model.entity.Friend;
import com.pidan.model.entity.User;
import com.pidan.model.request.friendrequest.FriendAddRequest;
import com.pidan.model.request.friendrequest.FriendDeleteRequest;
import com.pidan.model.request.friendrequest.FriendQuery;
import com.pidan.model.vo.UserVO;
import com.pidan.service.FriendService;
import com.pidan.mapper.FriendMapper;
import com.pidan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 73782
 * @description 针对表【friend(好友关系表)】的数据库操作Service实现
 * @createDate 2023-04-25 09:36:57
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend>
        implements FriendService {


    @Resource
    private UserService userService;

    @Resource
    private FriendMapper friendMapper;

    /**
     * 申请加好友
     *
     * @param loginUser
     * @param friendAddRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addFriend(User loginUser, FriendAddRequest friendAddRequest) {
        //先查询出目标对象
        Long toUserId = friendAddRequest.getUserId();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", toUserId);
        User one = userService.getOne(userQueryWrapper);
        if (one == null) {
            throw new BusinessException(ErrorCode.NOT_DATA, "用户不存在");
        }
        //获取出当前用户的id
        Long fromUserId = loginUser.getId();

        String remark = friendAddRequest.getRemark();
        //判断双方是否已经是好友
        Map<String, Object> map = new HashMap<>();
        map.put("fromUserId", fromUserId);
        map.put("toUserId", toUserId);
        if (friendMapper.isFriend(map) != null) {
            //已经是好友，则返回false
            return false;
        }
        // 新建好友申请
        Friend friend = new Friend();
        friend.setFromUserId(fromUserId);
        friend.setToUserId(toUserId);
        friend.setRemark(remark);
        boolean save = save(friend);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加失败");
        }
        return true;
    }

    /**
     * 接受好友申请
     *
     * @param loginUser
     * @param friendAddRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean acceptFriedn(User loginUser, FriendAddRequest friendAddRequest) {
        Long fromUserId = loginUser.getId();
        Long toUserId = friendAddRequest.getUserId();
        if (toUserId <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String remark = friendAddRequest.getRemark();
        // 判断好友申请是否存在
        Map<String, Object> map = new HashMap<>();
        map.put("fromUserId", fromUserId);
        map.put("toUserId", toUserId);
        Friend friend = friendMapper.getFriend(map);
        if (friend == null) {
            return false;
        }
        // 接受好友申请
        boolean result = friendMapper.acceptFriend(map);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接受失败");
        }
        // 新建好友申请
        friend = new Friend();
        friend.setFromUserId(fromUserId);
        friend.setToUserId(toUserId);
        friend.setRemark(remark);
        friend.setAccepted(1);
        boolean save = save(friend);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加失败");
        }
        return true;
    }


    /**
     * 查看好友列表
     *
     * @param loginUserId
     * @return
     */
    @Override
    public List<UserVO> selectFriendList(Long loginUserId) {
        if (loginUserId <= 0) {
            throw new BusinessException(ErrorCode.NOT_DATA, "未添加好友");
        }
        QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
        friendQueryWrapper.eq("toUserId", loginUserId);
        List<Friend> friendList = list(friendQueryWrapper);
        List<UserVO> userVOList = new ArrayList<>();
        for (Friend friend : friendList) {
            Long fromUserId = friend.getFromUserId();
            User user = userService.getById(fromUserId);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        return userVOList;
    }

    /**
     * 删除好友
     *
     * @param loginUserId
     * @param friendDeleteRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFriend(Long loginUserId, FriendDeleteRequest friendDeleteRequest) {
        Long toUserId = friendDeleteRequest.getUserId();
        if (toUserId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
        friendQueryWrapper.eq("fromUserId", loginUserId);
        friendQueryWrapper.eq("toUserId", toUserId);
        boolean remove = this.remove(friendQueryWrapper);
        if (!remove) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除好友失败");

        }
        QueryWrapper<Friend> friendQueryWrapper1 = new QueryWrapper<>();
        friendQueryWrapper1.eq("fromUserId", toUserId);
        friendQueryWrapper1.eq("toUserId", loginUserId);
        boolean res = this.remove(friendQueryWrapper1);
        if (!res) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除好友失败");

        }
        return true;
    }

    @Override
    public UserVO getFriendOne(Long loginUserId, FriendQuery friendQuery) {
        Long toUserid = friendQuery.getUserId();
        QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
        friendQueryWrapper.eq("fromUserId", loginUserId);
        friendQueryWrapper.eq("toUserId", toUserid);
        Friend friend = getOne(friendQueryWrapper);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", toUserid);
        User user = userService.getOne(userQueryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_DATA, "没有该好友");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }


}




