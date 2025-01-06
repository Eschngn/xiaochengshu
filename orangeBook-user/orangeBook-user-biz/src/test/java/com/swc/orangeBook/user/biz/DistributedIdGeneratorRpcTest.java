package com.swc.orangeBook.user.biz;

import com.swc.orangeBook.user.biz.rpc.DistributedIdGeneratorRpcService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/17 22:53
 */
@SpringBootTest
@Slf4j
public class DistributedIdGeneratorRpcTest {
    @Resource
    private DistributedIdGeneratorRpcService distributedIdGeneratorRpcService;

    @Test
    void getId(){
        String xiaochengshuId = distributedIdGeneratorRpcService.getXiaochengshuId();
        log.info("拿到分布式id:{}",xiaochengshuId);
    }
}
