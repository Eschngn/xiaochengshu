package com.swc.orangeBook.oss.biz.service;

import com.swc.framework.common.response.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/16 13:01
 */
public interface FileService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    Response<?> uploadFile(MultipartFile file);
}
