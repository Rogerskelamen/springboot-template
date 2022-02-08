package com.rokelamen.blog.controller;

import com.rokelamen.blog.service.TagService;
import com.rokelamen.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/hot/{limit}")
    public Result host(@PathVariable("limit") int limit) {
        return tagService.hots(limit);
    }

    @GetMapping
    public Result getTags() {
        return tagService.findAll();
    }

    @GetMapping("/detail")
    public Result getTagsDetail() {
        return tagService.findAllDetail();
    }

    @GetMapping("/detail/{id}")
    public Result getTagsDetailById(@PathVariable("id") Long id) {
        return tagService.getTagsDetailById(id);
    }
}
