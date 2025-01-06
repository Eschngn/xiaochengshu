package com.swc.orangeBook.oss.api;

import com.swc.framework.common.response.Response;
import com.swc.orangeBook.oss.config.FeignFormConfig;
import com.swc.orangeBook.oss.constant.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/16 19:35
 */
@FeignClient(name= ApiConstants.SERVICE_NAME,configuration = FeignFormConfig.class)
public interface FileFeignApi {
    String PREFIX = "/file";

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = PREFIX + "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<?> uploadFile(@RequestPart(value = "file") MultipartFile file);
}
