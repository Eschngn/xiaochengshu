package com.swc.orangeBook.oss.biz.service.impl;

import com.swc.framework.common.response.Response;
import com.swc.orangeBook.oss.biz.service.FileService;
import com.swc.orangeBook.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/16 13:01
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Resource
    private FileStrategy fileStrategy;
    private static final String BUCKET_NAME = "xiaochengshu";
    @Override
    public Response<?> uploadFile(MultipartFile file) {
        // 上传文件
        String url = fileStrategy.uploadFile(file, BUCKET_NAME);
        return Response.success(url);
    }
}
