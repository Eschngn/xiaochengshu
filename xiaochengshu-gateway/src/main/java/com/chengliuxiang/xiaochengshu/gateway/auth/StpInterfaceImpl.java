package com.chengliuxiang.xiaochengshu.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;

public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        // 返回此 loginId 拥有的权限列表

        // todo 从 redis 获取

        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        // 返回此 loginId 拥有的角色列表

        // todo 从 redis 获取

        return Collections.emptyList();
    }
}
