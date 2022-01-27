package com.rokelamen.blog.service;

import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    // 多对多

    /**
     * 通过文章id查找所有的Tag id，从而得到所有的tag
     * @param articleId
     * @return TagVo的集合
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 最热标签返回
     * @param limit 最多的标签个数
     * @return  TagVo转的返回类
     */
    Result hots(int limit);
}
