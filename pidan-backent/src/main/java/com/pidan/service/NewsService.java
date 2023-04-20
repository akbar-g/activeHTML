package com.pidan.service;

import com.pidan.model.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 73782
* @description 针对表【news(新闻表)】的数据库操作Service
* @createDate 2023-04-03 17:48:14
*/
public interface NewsService extends IService<News> {

    List<News> getAllNews();

    List<News> getNewsListByKeyWord(String keyWord);
}
