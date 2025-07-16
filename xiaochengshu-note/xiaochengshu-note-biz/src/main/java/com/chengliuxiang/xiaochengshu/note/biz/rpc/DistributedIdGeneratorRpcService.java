package com.chengliuxiang.xiaochengshu.note.biz.rpc;

import com.chengliuxiang.xiaochengshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DistributedIdGeneratorRpcService {

    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    public String getSnowflakeId(){
        return distributedIdGeneratorFeignApi.getSnowflakeId("xiaochengshu");
    }
}
