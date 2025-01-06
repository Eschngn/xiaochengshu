package com.swc.orangeBook.user.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/16 15:14
 */
@SpringBootApplication
@MapperScan("com.swc.orangeBook.user.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.swc.orangeBook")
public class OrangeBookUserBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrangeBookUserBizApplication.class,args);
    }
}
