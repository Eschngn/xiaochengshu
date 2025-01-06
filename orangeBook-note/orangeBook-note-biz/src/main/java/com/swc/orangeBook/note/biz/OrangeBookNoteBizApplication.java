package com.swc.orangeBook.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/17 23:54
 */
@SpringBootApplication
@MapperScan("com.swc.orangeBook.note.biz.domain.mapper")
@EnableFeignClients("com.swc.orangeBook")
public class OrangeBookNoteBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrangeBookNoteBizApplication.class, args);
    }
}
