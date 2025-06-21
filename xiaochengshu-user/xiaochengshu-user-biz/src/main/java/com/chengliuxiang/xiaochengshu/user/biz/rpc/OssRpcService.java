package com.chengliuxiang.xiaochengshu.user.biz.rpc;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.oss.api.FileFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OssRpcService {
    @Resource
    private FileFeignApi fileFeignApi;

    public String uploadFile(MultipartFile file){
        Response<?> response = fileFeignApi.uploadFile(file);
        if(!response.isSuccess()){
            return null;
        }
        return response.getData().toString();
    }
}
