package com.rokelamen.blog.service;

import com.rokelamen.blog.vo.CategoryVo;
import com.rokelamen.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();
}
