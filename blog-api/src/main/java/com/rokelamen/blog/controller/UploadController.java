package com.rokelamen.blog.controller;

import com.rokelamen.blog.common.aop.LogAnnotation;
import com.rokelamen.blog.utils.QiniuUtils;
import com.rokelamen.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    // @LogAnnotation(module = "上传图片", operation = "图片上传")
    public Result upload(@RequestParam("image")MultipartFile file) {
        // 原始文件名称 比如 aa.png
        String originalFileName = file.getOriginalFilename();
        // 唯一的文件名称(肯定不能用原始文件名)
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFileName, ".");
        // 上传文件 上传到哪呢？ 七牛云

        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }else {
            return Result.fail(20001, "上传失败");
        }
    }
}
