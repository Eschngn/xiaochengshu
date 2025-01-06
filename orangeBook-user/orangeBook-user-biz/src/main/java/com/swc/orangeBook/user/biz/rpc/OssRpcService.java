package com.swc.orangeBook.user.biz.rpc;

import com.swc.framework.common.response.Response;
import com.swc.orangeBook.oss.api.FileFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/16 19:53
 */
@Component
public class OssRpcService {
    @Resource
    private FileFeignApi fileFeignApi;

    public String uploadFile(MultipartFile file){
        // 调用对象存储服务上传文件
        Response<?> response = fileFeignApi.uploadFile(file);
        if(!response.isSuccess()){
            return null;
        }
        // 返回图片访问链接
        return (String) response.getData();
    }
}
