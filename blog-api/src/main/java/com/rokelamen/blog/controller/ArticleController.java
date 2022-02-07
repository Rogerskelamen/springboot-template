package com.rokelamen.blog.controller;

import com.rokelamen.blog.common.aop.LogAnnotation;
import com.rokelamen.blog.service.ArticleService;
import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.params.ArticleParams;
import com.rokelamen.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 使用json格式进行交互
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParams
     * @return Result对象来判断是否传入成功
     */
    @PostMapping
    // 加上此注解，代表要对此接口记录日志
    @LogAnnotation(module="文章", operation="获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("/hot/{limit}")
    public Result hotArticle(@PathVariable int limit) {
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("/new/{limit}")
    public Result newArticles(@PathVariable int limit) {
        return articleService.newArticles(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    @GetMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParams articleParams) {
        return articleService.publish(articleParams);
    }
}
