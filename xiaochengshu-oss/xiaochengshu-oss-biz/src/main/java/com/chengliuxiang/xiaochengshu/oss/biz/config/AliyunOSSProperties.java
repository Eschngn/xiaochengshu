package com.chengliuxiang.xiaochengshu.oss.biz.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RefreshScope
public class AliyunOSSProperties {

    @Value("${aliyun-oss.endpoint}")
    private String endpoint;

    @Value("${aliyun-oss.accessKey}")
    private String accessKey;

    @Value("${aliyun-oss.secretKey}")
    private String secretKey;

    @Value("${aliyun-oss.bucketName}")
    private String bucketName;

    @Value("${aliyun-oss.folderName}")
    private String folderName;
}
