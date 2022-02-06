package com.rokelamen.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.rokelamen.blog.mapper.ArticleMapper;
import com.rokelamen.blog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    @Autowired
    private ArticleMapper articleMapper;

    @Async("taskExecutor")
    public void updateArticleViewCount(Article article) {

        Integer viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        updateWrapper.eq(Article::getViewCounts, viewCounts); // 乐观锁，如果操作的时候发现与期望的阅读量不一致，修改失败
        // update article set view_count=100 where view_count=99 and id=1
        articleMapper.update(articleUpdate, updateWrapper);
        try {
            Thread.sleep(5000);
            System.out.println("更新完成了....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
