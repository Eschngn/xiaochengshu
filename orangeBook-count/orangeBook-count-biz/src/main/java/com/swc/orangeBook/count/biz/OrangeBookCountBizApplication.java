package com.swc.orangeBook.count.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/10/10 23:14
 */
@SpringBootApplication
@MapperScan("com.swc.orangeBook.count.biz.domain.mapper")
public class OrangeBookCountBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrangeBookCountBizApplication.class);
    }
}
