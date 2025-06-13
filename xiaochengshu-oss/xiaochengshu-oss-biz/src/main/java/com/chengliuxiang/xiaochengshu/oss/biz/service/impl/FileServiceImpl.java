package com.chengliuxiang.xiaochengshu.oss.biz.service.impl;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.oss.biz.service.FileService;
import com.chengliuxiang.xiaochengshu.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileServiceImpl implements FileService {
    @Resource
    private FileStrategy fileStrategy;

    @Override
    public Response<?> uploadFile(MultipartFile file) {
        fileStrategy.uploadFile(file, "xiaohashu");

        return Response.success();
    }
}
