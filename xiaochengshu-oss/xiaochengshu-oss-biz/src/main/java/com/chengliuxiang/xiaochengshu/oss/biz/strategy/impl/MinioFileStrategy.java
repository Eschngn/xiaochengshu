package com.chengliuxiang.xiaochengshu.oss.biz.strategy.impl;

import com.chengliuxiang.xiaochengshu.oss.biz.config.MinioProperties;
import com.chengliuxiang.xiaochengshu.oss.biz.strategy.FileStrategy;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public class MinioFileStrategy implements FileStrategy {

    @Resource
    private MinioProperties minioProperties;

    @Resource
    private MinioClient minioClient;

    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file) {
        log.info("## 上传文件至 Minio ...");

        if (file == null || file.getSize() == 0) {
            log.error("==> 上传文件异常：文件大小为空 ...");
            throw new RuntimeException("文件大小不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        String key = UUID.randomUUID().toString().replace("-", "");
        String suffix = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));

        String objectName = String.format("%s%s", key, suffix);

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(contentType)
                .build());

        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), objectName);
        log.info("==> 上传文件至 Minio 成功，访问路径: {}", url);
        return url;
    }
}
