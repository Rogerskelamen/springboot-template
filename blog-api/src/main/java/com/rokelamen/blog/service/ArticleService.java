package com.rokelamen.blog.service;

import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.params.ArticleParams;
import com.rokelamen.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams 分页参数
     * @return 文章数据的Vo形式
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发布服务
     * @param articleParams
     * @return
     */
    Result publish(ArticleParams articleParams);
}
