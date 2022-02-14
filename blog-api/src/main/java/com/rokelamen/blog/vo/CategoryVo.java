package com.rokelamen.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("目录")
public class CategoryVo {

    @ApiModelProperty("主键")
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
