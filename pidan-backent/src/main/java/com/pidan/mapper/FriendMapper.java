package com.pidan.mapper;

import com.pidan.model.entity.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.HashMap;
import java.util.Map;

/**
* @author 73782
* @description 针对表【friend(好友关系表)】的数据库操作Mapper
* @createDate 2023-04-25 09:36:57
* @Entity com.pidan.model.entity.Friend
*/
public interface FriendMapper extends BaseMapper<Friend> {

    Friend isFriend(Map map);

    Friend getFriend(Map map);

    Boolean acceptFriend(Map map);
}





