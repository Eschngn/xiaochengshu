package com.swc.orangebook.auth;/**
 * @Description:
 * @author swc
 * @date 2024/9/6 13:44
 */

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: orangeBook
 *
 * @description: 明文加密测试
 *
 * @author: Wilson
 *
 * @create: 2024-09-06 13:44
 **/
@Slf4j
@SpringBootTest
public class DruidTests {
    /**
     * Druid 密码加密
     */
    @Test
    @SneakyThrows
    void testEncodePassword() {
        // 实际密码
        String password = "123456";
        String[] arr = ConfigTools.genKeyPair(512);

        // 私钥,用于加密
        log.info("privateKey: {}", arr[0]);
        // 公钥,用于解密
        log.info("publicKey: {}", arr[1]);

        // 通过私钥加密密码
        String encodePassword = ConfigTools.encrypt(arr[0], password);
        log.info("password: {}", encodePassword);
    }
}
