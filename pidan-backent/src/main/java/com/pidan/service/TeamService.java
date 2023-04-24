package com.pidan.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pidan.model.entity.Team;
import com.pidan.model.entity.User;
import com.pidan.model.request.teamrequest.TeamJoinRequest;
import com.pidan.model.request.teamrequest.TeamQuery;
import com.pidan.model.request.teamrequest.TeamQuitRequest;
import com.pidan.model.request.teamrequest.TeamUpdateRequest;
import com.pidan.model.vo.TeamUserVO;

import java.util.List;

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

    /**
     * 查询队伍
     * @param teamQuery
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery,boolean isAdmin);

    /**
     * 修改队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest,User loginUser);


    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loingUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest ,User loingUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);
}
