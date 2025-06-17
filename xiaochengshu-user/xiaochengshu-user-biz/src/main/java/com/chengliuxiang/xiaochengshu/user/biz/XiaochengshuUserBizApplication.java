package com.chengliuxiang.xiaochengshu.user.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.chengliuxiang.xiaochengshu.user.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.chengliuxiang.xiaochengshu")
public class XiaochengshuUserBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaochengshuUserBizApplication.class, args);
    }
}
