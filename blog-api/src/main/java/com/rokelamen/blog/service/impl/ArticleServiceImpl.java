package com.rokelamen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rokelamen.blog.dos.Archives;
import com.rokelamen.blog.mapper.ArticleBodyMapper;
import com.rokelamen.blog.mapper.ArticleMapper;
import com.rokelamen.blog.mapper.ArticleTagMapper;
import com.rokelamen.blog.pojo.Article;
import com.rokelamen.blog.pojo.ArticleBody;
import com.rokelamen.blog.pojo.ArticleTag;
import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.service.*;
import com.rokelamen.blog.utils.UserThreadLocal;
import com.rokelamen.blog.vo.ArticleBodyVo;
import com.rokelamen.blog.vo.ArticleVo;
import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.TagVo;
import com.rokelamen.blog.vo.params.ArticleBodyParams;
import com.rokelamen.blog.vo.params.ArticleParams;
import com.rokelamen.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records, true, true));
    }
    // @Override
    // public Result listArticle(PageParams pageParams) {
    //     /**
    //      * 1. ??????????????????artilce?????????
    //      */
    //     Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
    //     LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    //     if (pageParams.getCategoryId() != null) {
    //         // and category_id = #{categoryId}
    //         queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
    //     }
    //     List<Long> articleIdList = new ArrayList<>();
    //     if (pageParams.getTagId() != null) {
    //         // article?????? ?????????tag?????? ?????????????????????
    //         LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
    //         articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
    //         List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
    //         for (ArticleTag articleTag : articleTags) {
    //             articleIdList.add(articleTag.getArticleId());
    //         }
    //         if (articleIdList.size() > 0) {
    //             // and id in (1, 2, 3)
    //             queryWrapper.in(Article::getId, articleIdList);
    //         }
    //     }
    //     // ????????????????????????
    //     // order by create_date desc
    //     queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
    //     Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
    //     List<Article> records = articlePage.getRecords();
    //     // ?????????????????????????????????,??????????????????VO??????
    //     List<ArticleVo> articleVoList = copyList(records, true, true);
    //     return Result.success(articleVoList);
    // }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        // select id, title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        // select id, title from article order by created_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return Result.success(archives);
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. ??????id????????????
         * 2. ?????? bodyId??? categoryid ??????????????????
         */
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true, true, true);

        // ?????????????????????????????????????????????????????????
        // ?????????????????????????????????????????????????????????
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????
        // ???????????????????????????????????????????????????????????????
        // ????????? ??????????????????????????????????????????????????????????????????????????????
        threadService.updateArticleViewCount(article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParams articleParams) {
        // ??????????????????????????????????????????
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1. ?????????????????? ???????????????Article??????
         * 2. ??????id ?????????????????????
         * 3. ?????? ????????????????????? ???????????????
         * 4. body???????????? article bodyId
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParams.getTitle());
        article.setSummary(articleParams.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(articleParams.getCategory().getId());
        // ?????????????????????????????????id
        articleMapper.insert(article);
        // tag
        List<TagVo> tags = articleParams.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        // body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContentHtml(articleParams.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        // ??????????????????articleBodyId
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        // ????????????????????????article???id?????????map?????????????????????
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    // ??????????????????article????????????????????????articleVo
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article :
                records) {
            articleVoList.add(copy(article, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article :
                records) {
            articleVoList.add(copy(article, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
