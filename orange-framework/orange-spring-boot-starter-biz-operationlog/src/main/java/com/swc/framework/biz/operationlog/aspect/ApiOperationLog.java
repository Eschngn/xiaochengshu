package com.swc.framework.biz.operationlog.aspect;

import java.lang.annotation.*;

/**
 * @author swc
 * @Description:
 * @date 2024/9/6 10:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ApiOperationLog {
    /**
     * API 功能描述
     *
     * @return
     */
    String description() default "";
}
