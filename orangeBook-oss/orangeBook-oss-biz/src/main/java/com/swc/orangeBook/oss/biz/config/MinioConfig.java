package com.swc.orangeBook.oss.biz.config;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/16 13:24
 */
@Configuration
public class MinioConfig {
    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient(){
        // 构建 Minio 客户端
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
