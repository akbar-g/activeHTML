package com.pidan.controller;

import com.pidan.common.BaseResponse;
import com.pidan.common.ResultUtils;
import com.pidan.model.entity.News;
import com.pidan.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新闻
 * @author 黄大头
 * @date 2023年03月25日 21:26
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    /**
     * 查询所有新闻信息
     * @return
     */
    @GetMapping("/getAll")
    public BaseResponse<List<News>> getAllNews(){
        List<News> lists = newsService.getAllNews();
        return ResultUtils.success(lists);
    }

    @PostMapping("/selectByKeyWord")
    public BaseResponse<List<News>> getNewsListByKeyWord( String keyWord){
        if (StringUtils.isEmpty(keyWord)){
            List<News> allNews = newsService.getAllNews();
            return ResultUtils.success(allNews);
        }
        List<News> lists = newsService.getNewsListByKeyWord(keyWord);
        return ResultUtils.success(lists);
    }

}
