package com.chengliuxiang.xiaochengshu.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chengliuxiang.xiaochengshu.auth.domain.mapper")
public class XiaochengshuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaochengshuAuthApplication.class, args);
    }

}
