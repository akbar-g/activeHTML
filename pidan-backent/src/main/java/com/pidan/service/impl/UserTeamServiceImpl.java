package com.pidan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pidan.mapper.UserTeamMapper;
import com.pidan.model.entity.UserTeam;
import com.pidan.service.UserTeamService;
import org.springframework.stereotype.Service;

/**
* @author 73782
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2023-04-20 14:57:21
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




