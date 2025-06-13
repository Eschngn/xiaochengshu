package com.chengliuxiang.xiaochengshu.oss.biz.factory;

import com.chengliuxiang.xiaochengshu.oss.biz.strategy.FileStrategy;
import com.chengliuxiang.xiaochengshu.oss.biz.strategy.impl.AliyunOSSFileStrategy;
import com.chengliuxiang.xiaochengshu.oss.biz.strategy.impl.MinioFileStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStrategyFactory {

    @Value("${storage.type}")
    private String storageType;

    @Bean
    @RefreshScope
    public FileStrategy getFileStrategy() {
        if (StringUtils.equals(storageType, "minio")) {
            return new MinioFileStrategy();
        } else if (StringUtils.equals(storageType, "aliyun")) {
            return new AliyunOSSFileStrategy();
        }
        throw new IllegalArgumentException("不可用的存储类型");
    }
}
