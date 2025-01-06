package com.swc.orangeBook.oss.biz.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wilson
 * @Description: 文件策略接口
 * @date 2024/9/16 12:52
 */
public interface FileStrategy {
    /**
     * 文件上传
     *
     * @param file
     * @param bucketName
     * @return
     */
    String uploadFile(MultipartFile file, String bucketName);
}
