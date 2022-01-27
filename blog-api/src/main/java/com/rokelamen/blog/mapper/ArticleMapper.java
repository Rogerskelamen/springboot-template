package com.rokelamen.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rokelamen.blog.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
