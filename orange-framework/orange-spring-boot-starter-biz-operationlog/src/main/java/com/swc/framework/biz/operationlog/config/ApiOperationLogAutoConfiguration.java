package com.swc.framework.biz.operationlog.config;
/**
 * @Description:
 * @author swc
 * @date 2024/9/6 11:15
 */

import com.swc.framework.biz.operationlog.aspect.ApiOperationLogAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @program: orangeBook
 *
 * @description: 日志切面自动配置类
 *
 * @author: Wilson
 *
 * @create: 2024-09-06 11:15
 **/
@AutoConfiguration
public class ApiOperationLogAutoConfiguration {
    @Bean
    public ApiOperationLogAspect apiOperationLogAspect(){
        return new ApiOperationLogAspect();
    }
}
