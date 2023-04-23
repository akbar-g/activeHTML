package com.pidan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pidan.common.ErrorCode;
import com.pidan.model.entity.News;
import com.pidan.exception.BusinessException;
import com.pidan.service.NewsService;
import com.pidan.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 73782
* @description 针对表【news(新闻表)】的数据库操作Service实现
* @createDate 2023-04-03 17:48:14
*/
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
    implements NewsService{

    @Resource
    private NewsMapper newsMapper;
    /**
     * 查询所有新闻
     * @return
     */
    @Override
    public List<News> getAllNews() {
        List<News> lists = list();
        if (lists.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_DATA);
        }
        return lists;
    }

    @Override
    public List<News> getNewsListByKeyWord(String keyWord) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",keyWord);
        List<News> list = newsMapper.selectList(queryWrapper);
        if (list.isEmpty()){
            return null;
        }
        return list;
    }
}




