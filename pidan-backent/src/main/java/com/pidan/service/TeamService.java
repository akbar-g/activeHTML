package com.pidan.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pidan.model.entity.Team;
import com.pidan.model.entity.User;

/**
* @author 73782
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2023-04-20 14:56:56
*/
public interface TeamService extends IService<Team> {

    /**
     * 添加队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);
}
