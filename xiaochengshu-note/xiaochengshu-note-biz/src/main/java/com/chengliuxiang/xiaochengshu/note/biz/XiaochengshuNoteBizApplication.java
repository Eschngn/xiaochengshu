package com.chengliuxiang.xiaochengshu.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.chengliuxiang.xiaochengshu.note.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.chengliuxiang.xiaochengshu")
public class XiaochengshuNoteBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaochengshuNoteBizApplication.class, args);
    }
}
