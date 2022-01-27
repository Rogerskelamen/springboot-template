package com.rokelamen.blog.service;

import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams 分页参数
     * @return 文章数据的Vo形式
     */
    Result listArticle(PageParams pageParams);
}
