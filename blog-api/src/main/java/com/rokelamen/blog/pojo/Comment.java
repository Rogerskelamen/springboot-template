package com.rokelamen.blog.pojo;

import lombok.Data;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
