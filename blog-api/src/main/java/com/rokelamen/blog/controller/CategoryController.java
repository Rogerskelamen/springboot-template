package com.rokelamen.blog.controller;

import com.rokelamen.blog.service.CategoryService;
import com.rokelamen.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }

    @GetMapping("/detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id) {
        return categoryService.categoriesDetailById(id);
    }
}
