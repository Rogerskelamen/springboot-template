package com.rokelamen.blog.service;

import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.params.CommentParams;

public interface CommentsService {

    /**
     * 根据文章id得到评论
     * @param id
     * @return
     */
    Result commentsByArticleById(Long id);

    /**
     * 创建评论
     * @param commentParams
     * @return
     */
    Result comment(CommentParams commentParams);
}
