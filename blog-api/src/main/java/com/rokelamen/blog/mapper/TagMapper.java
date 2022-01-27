package com.rokelamen.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rokelamen.blog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);
}
