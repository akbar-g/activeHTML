package com.pidan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pidan.common.BaseResponse;
import com.pidan.common.ErrorCode;
import com.pidan.common.ResultUtils;
import com.pidan.exception.BusinessException;
import com.pidan.model.entity.Team;
import com.pidan.model.entity.User;
import com.pidan.model.request.teamrequest.TeamAddRequest;
import com.pidan.model.request.teamrequest.TeamQuery;
import com.pidan.service.TeamService;
import com.pidan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 黄大头
 * @date 2023年04月20日 15:05
 */
@RestController
@RequestMapping("/team")
@Slf4j
//@CrossOrigin(origins = {"http://localhost:5173/"})
public class TeamController {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private TeamService teamService;

    @PostMapping("/add")
    public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request){
        if (teamAddRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Team team = new Team();
        BeanUtils.copyProperties(teamAddRequest,team);
        long teamId = teamService.addTeam(team,loginUser);
        return ResultUtils.success(teamId);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeam(@RequestBody long  id){
        if (id <= 0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        boolean result = teamService.removeById(id);
        if (!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateTeam(@RequestBody Team   team){
        if (team == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        boolean save = teamService.updateById(team);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/get")
    public  BaseResponse<Team> getTeamById(long id){
        if (id <= 0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Team team = teamService.getById(id);
        if (team == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(team);
    }

    @GetMapping("/list")
    public  BaseResponse<List<Team>> listTeams(TeamQuery teamQuery){
        if (teamQuery == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Team team = new Team();
        BeanUtils.copyProperties(team,teamQuery);
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        List<Team> teamList = teamService.list(teamQueryWrapper);
        return ResultUtils.success(teamList);
    }

    @GetMapping("/list/page")
    public  BaseResponse<Page<Team>> listPageTeams(TeamQuery teamQuery){
        if (teamQuery == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Team team = new Team();
        BeanUtils.copyProperties(team,teamQuery);
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        Page<Team> page = new Page<>(teamQuery.getPageNum(), teamQuery.getPageSize());
        Page<Team> resultPage = teamService.page(page, teamQueryWrapper);
        return ResultUtils.success(resultPage);
    }
}
