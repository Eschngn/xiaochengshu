package com.swc.orangeBook.count.biz.consumer;

import cn.hutool.core.collection.CollUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.swc.framework.common.util.JsonUtil;
import com.swc.orangeBook.count.biz.domain.mapper.UserCountDOMapper;
import com.swc.orangeBook.count.biz.constant.MQConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Wilson
 * @Description: 计数: 粉丝数存库
 * @date 2024/10/13 14:14
 */
@Component
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_COUNT_FANS_2_DB, // Group 组
        topic = MQConstants.TOPIC_COUNT_FANS_2_DB // 主题 Topic
)
@Slf4j
public class CountFans2DBConsumer implements RocketMQListener<String> {
    // 每秒创建 5000 个令牌
    private RateLimiter rateLimiter=RateLimiter.create(5000);
    @Resource
    private UserCountDOMapper userCountDOMapper;

    @Override
    public void onMessage(String body) {

        // 流量削峰：通过获取令牌，如果没有令牌可用，将阻塞，直到获得
        rateLimiter.acquire();

        log.info("## 消费到了 MQ 【计数: 粉丝数入库】, {}...", body);

        Map<Long,Integer> countMap=null;
        try {
            countMap = JsonUtil.parseMap(body, Long.class, Integer.class);
        } catch (Exception e) {
            log.error("## 解析 JSON 字符串异常", e);
        }

        if (CollUtil.isNotEmpty(countMap)) {
            // 判断数据库中，若目标用户的记录不存在，则插入；若记录已存在，则直接更新
            countMap.forEach((k, v) -> userCountDOMapper.insertOrUpdateFansTotalByUserId(v, k));
        }
    }

}
