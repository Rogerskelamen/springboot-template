package com.rokelamen.blog.common.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    // 数据在缓存中存活的时间
    long expire() default 1 * 60 * 1000;

    String name() default "";
}
