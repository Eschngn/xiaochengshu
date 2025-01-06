package com.swc.orangeBook.note;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/19 10:46
 */
@SpringBootTest
@Slf4j
public class LogBackTest {
    @Test
    void testLog() {
        log.info("这是一行 Info 级别日志");
        log.warn("这是一行 Warn 级别日志");
        log.error("这是一行 Error 级别日志");
        // 占位符
        String author = "swc";
        log.info("这是一行带有占位符日志，作者：{}", author);
    }
}
