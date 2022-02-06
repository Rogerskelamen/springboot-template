package com.rokelamen.blog.controller;

import com.rokelamen.blog.service.CommentsService;
import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long id) {
        return commentsService.commentsByArticleById(id);
    }

    @PostMapping("/create/change")
    public Result createComment(@RequestBody CommentParams commentParams) {
        return commentsService.comment(commentParams);
    }
}
