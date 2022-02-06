package com.rokelamen.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rokelamen.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
