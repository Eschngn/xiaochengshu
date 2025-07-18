package com.chengliuxiang.xiaochengshu.note.biz;

import com.chengliuxiang.framework.common.util.JsonUtils;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.FindNoteDetailRspVO;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class NullTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final Cache<Long, String> LOCAL_CACHE = Caffeine.newBuilder()
            .initialCapacity(10000) // 设置初始容量为 10000 个条目
            .maximumSize(10000) // 设置缓存的最大容量为 10000 个条目
            .expireAfterWrite(1, TimeUnit.HOURS) // 设置缓存条目在写入后 1 小时过期
            .build();

    @Test
    public void RedisTest() {
        String test = redisTemplate.opsForValue().get("test");
        System.out.println(StringUtils.isBlank(test)); // false
        FindNoteDetailRspVO findNoteDetailRspVO = JsonUtils.parseObject(test, FindNoteDetailRspVO.class);
        System.out.println(findNoteDetailRspVO); // null
        System.out.println(Objects.isNull(findNoteDetailRspVO)); // true

        LOCAL_CACHE.put(111L,"null");
        String localCache = LOCAL_CACHE.getIfPresent(111L);
        System.out.println(localCache); // null
        System.out.println(StringUtils.isBlank(localCache)); // false

    }
}
