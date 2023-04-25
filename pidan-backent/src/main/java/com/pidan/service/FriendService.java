package com.pidan.service;

import com.pidan.model.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pidan.model.entity.User;
import com.pidan.model.request.friendrequest.FriendAddRequest;
import com.pidan.model.request.friendrequest.FriendDeleteRequest;
import com.pidan.model.request.friendrequest.FriendQuery;
import com.pidan.model.vo.UserVO;

import java.util.List;

/**
* @author 73782
* @description 针对表【friend(好友关系表)】的数据库操作Service
* @createDate 2023-04-25 09:36:57
*/
public interface FriendService extends IService<Friend> {

    boolean addFriend(User loginUser, FriendAddRequest friendAddRequest);

    boolean acceptFriedn(User loginUser, FriendAddRequest friendAddRequest);

    List<UserVO> selectFriendList(Long loginUserId);

    boolean deleteFriend(Long loginUserId, FriendDeleteRequest friendDeleteRequest);

    UserVO getFriendOne(Long loginUserId, FriendQuery friendQuery);
}
