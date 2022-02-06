package com.rokelamen.blog.pojo;

import lombok.Data;

@Data
public class Article {
    public static final int Article_TOP = 1;
    public static final int Article_Common = 0;

    private Long id;

    private Integer commentCounts;

    /**
     * 创建时间
     */
    private Long createDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    /**
     * 置顶, 权重越高越置顶
     */
    private Integer weight;

    private Long authorId;

    private Long bodyId;

    private Long categoryId;

}
