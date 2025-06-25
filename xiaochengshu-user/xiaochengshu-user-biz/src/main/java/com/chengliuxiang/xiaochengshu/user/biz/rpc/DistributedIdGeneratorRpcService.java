package com.chengliuxiang.xiaochengshu.user.biz.rpc;

import com.chengliuxiang.xiaochengshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DistributedIdGeneratorRpcService {
    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    private static final String BIZ_TAG_XIAOCHENGSHU_ID = "leaf-segment-xiaochengshu-id";

    private static final String BIZ_TAG_USER_ID = "leaf-segment-user-id";

    public String getXiaochengshuId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_XIAOCHENGSHU_ID);
    }

    public String getUserId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_USER_ID);
    }
}
