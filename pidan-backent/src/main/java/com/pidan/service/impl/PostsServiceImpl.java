package com.pidan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pidan.common.ErrorCode;
import com.pidan.model.entity.Posts;
import com.pidan.exception.BusinessException;
import com.pidan.service.PostsService;
import com.pidan.mapper.PostsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author 73782
 * @description 针对表【posts(新闻表)】的数据库操作Service实现
 * @createDate 2023-04-04 14:55:58
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts>
        implements PostsService {

    @Override
    public void validPost(Posts posts, boolean add) {
        if (posts == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = posts.getTitle();
        String content = posts.getContent();
        String tags = posts.getTags();
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 40) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }
}




