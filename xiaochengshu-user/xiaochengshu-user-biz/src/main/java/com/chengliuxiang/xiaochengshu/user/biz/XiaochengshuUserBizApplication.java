package com.chengliuxiang.xiaochengshu.user.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chengliuxiang.xiaochengshu.user.biz.domain.mapper")
public class XiaochengshuUserBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaochengshuUserBizApplication.class, args);
    }
}
