package com.chengliuxiang.xiaochengshu.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import com.chengliuxiang.xiaochengshu.gateway.constant.RedisKeyConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class AddUserId2HeaderFilter implements GlobalFilter {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * Header 头中 Token 的 Key
     */
    private static final String TOKEN_HEADER_KEY = "Authorization";

    /**
     * Token 前缀
     */
    private static final String TOKEN_HEADER_VALUE_PREFIX = "Bearer ";
    /**
     * 请求头中，用户 ID 的键
     */
    private static final String HEADER_USER_ID = "userId";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("==================> TokenConvertFilter");

        // 从请求头中获取 Token 数据
        List<String> tokenList = exchange.getRequest().getHeaders().get(TOKEN_HEADER_KEY);
        if (CollUtil.isEmpty(tokenList)) {
            // 若请求头中未携带 Token，则直接放行
            return chain.filter(exchange);
        }
        // 获取 Token 值
        String tokenValue = tokenList.get(0);
        // 将 Token 前缀去除
        String token = tokenValue.replace(TOKEN_HEADER_VALUE_PREFIX, "");
        // 构建 Redis Key
        String tokenRedisKey = RedisKeyConstants.SA_TOKEN_TOKEN_KEY_PREFIX + token;
        // 查询 Redis, 获取用户 ID
        Integer userId = (Integer) redisTemplate.opsForValue().get(tokenRedisKey);
        if(Objects.isNull(userId)){
            // 若没有登录，则直接放行
            return chain.filter(exchange);
        }

        log.info("## 当前登录的用户 ID: {}", userId);


        ServerWebExchange newExchange = exchange.mutate()
                .request(builder -> builder.header(HEADER_USER_ID, String.valueOf(userId)))// 将用户 ID 设置到请求头中
                .build();
        return chain.filter(newExchange);
    }
}
