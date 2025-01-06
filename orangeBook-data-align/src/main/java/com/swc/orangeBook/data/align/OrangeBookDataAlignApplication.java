package com.swc.orangeBook.data.align;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Wilson
 * @Description: 启动类
 * @date 2024/11/15 15:05
 */
@SpringBootApplication
@MapperScan("com.swc.orangeBook.data.align.domain.mapper")
public class OrangeBookDataAlignApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrangeBookDataAlignApplication.class,args);
    }
}
