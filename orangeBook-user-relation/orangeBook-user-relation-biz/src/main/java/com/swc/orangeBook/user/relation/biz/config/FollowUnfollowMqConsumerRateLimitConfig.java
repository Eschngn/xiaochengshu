package com.swc.orangeBook.user.relation.biz.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wilson
 * @Description: 关注、取关消费者令牌桶配置类
 * @date 2024/9/19 23:10
 */
@Configuration
@RefreshScope
public class FollowUnfollowMqConsumerRateLimitConfig {
    @Value("${mq-consumer.follow-unfollow.rate-limit}")
    private double rateLimit;

    @Bean
    @RefreshScope
    public RateLimiter rateLimiter() {
        return RateLimiter.create(rateLimit);
    }
}
