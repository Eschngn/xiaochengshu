package com.swc.orangeBook.user.relation.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/19 15:09
 */
@SpringBootApplication
@MapperScan("com.swc.orangeBook.user.relation.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.swc.orangeBook")
public class OrangeBookUserRelationBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrangeBookUserRelationBizApplication.class,args);
    }
}
