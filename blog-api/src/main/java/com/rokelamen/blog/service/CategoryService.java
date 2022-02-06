package com.rokelamen.blog.service;

import com.rokelamen.blog.vo.CategoryVo;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);
}
