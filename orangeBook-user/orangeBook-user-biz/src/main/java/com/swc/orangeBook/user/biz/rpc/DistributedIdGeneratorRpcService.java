package com.swc.orangeBook.user.biz.rpc;

import com.swc.orangeBook.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author Wilson
 * @Description: 分布式 ID 生成服务
 * @date 2024/9/17 22:43
 */
@Component
public class DistributedIdGeneratorRpcService {
    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;
    /**
     * Leaf 号段模式：小橙书 ID 业务标识
     */
        private static final String BIZ_TAG_XIAOCHENGSHU_ID = "leaf-segment-xiaochengshu-id";

    /**
     * Leaf 号段模式：用户 ID 业务标识
     */
    private static final String BIZ_TAG_USER_ID = "leaf-segment-user-id";

    /**
     * 调用分布式 ID 生成服务生成小橙书 ID
     *
     * @return
     */
    public String getXiaochengshuId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_XIAOCHENGSHU_ID);
    }

    /**
     * 调用分布式 ID 生成服务用户 ID
     *
     * @return
     */
    public String getUserId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_USER_ID);
    }
}
