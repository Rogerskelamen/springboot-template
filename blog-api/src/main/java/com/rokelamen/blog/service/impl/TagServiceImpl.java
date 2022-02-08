package com.rokelamen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rokelamen.blog.mapper.TagMapper;
import com.rokelamen.blog.pojo.Category;
import com.rokelamen.blog.pojo.Tag;
import com.rokelamen.blog.service.TagService;
import com.rokelamen.blog.vo.CategoryVo;
import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag :
                tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // 1. Mybatis-plus无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        /**
         * 1. 标签所拥有的文章数量最多
         * 2. 查询 根据tag_id 分组 计数，从大到小排列
         */
        List<Long> tagIds =  tagMapper.findHotTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)) {
            return Result.success(Collections.emptyList());
        }else {
            // 这里的查询次数是limit次（调用查询语句的次数就是limit次），如果需要sql优化的话最好使用一次查询
            // 也就是自己写sql语句，不用mybatis-plus
            List<Tag> tags = new ArrayList<>();
            for (long tagId :
                    tagIds) {
                tags.add(tagMapper.selectById(tagId));
            }
            return Result.success(tags);
        }
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);

        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<>());

        return Result.success(copyList(tagList));
    }

    @Override
    public Result getTagsDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }
}
