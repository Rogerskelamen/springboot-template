package com.rokelamen.blog.handler;

import com.rokelamen.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 对加了@Controller注解的方法进行拦处理 AOP的实现
@ControllerAdvice
public class AllExceptionHandler {

    // 进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.fail(999, ex.toString());
    }
}
