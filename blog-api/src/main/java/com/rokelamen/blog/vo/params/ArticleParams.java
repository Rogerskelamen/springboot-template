package com.rokelamen.blog.vo.params;

import com.rokelamen.blog.vo.CategoryVo;
import com.rokelamen.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParams {

    private Long id;

    private ArticleBodyParams body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
