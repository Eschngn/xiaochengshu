package com.swc.orangeBook.oss.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/16 19:48
 */
@Configuration
public class FeignFormConfig {
    @Bean
    public Encoder feignFormEncoder(){
        return new SpringFormEncoder();
    }
}
