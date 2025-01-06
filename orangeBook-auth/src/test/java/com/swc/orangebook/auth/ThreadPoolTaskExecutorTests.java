package com.swc.orangebook.auth;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;




/**
 * @author Wilson
 * @Description:
 * @date 2024/9/11 22:08
 */
@SpringBootTest
@Slf4j
public class ThreadPoolTaskExecutorTests {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    void testSubmit(){
        threadPoolTaskExecutor.submit(()->log.info("异步线程中说: 小橙书专栏"));
    }
}
