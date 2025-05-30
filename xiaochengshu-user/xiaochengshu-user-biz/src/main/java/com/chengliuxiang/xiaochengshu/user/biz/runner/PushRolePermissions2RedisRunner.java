package com.chengliuxiang.xiaochengshu.user.biz.runner;

import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.PermissionDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.RoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.RolePermissionDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RoleDOMapper roleDOMapper;

    @Resource
    private PermissionDOMapper permissionDOMapper;

    @Resource
    private RolePermissionDOMapper rolePermissionDOMapper;

    private static final String PUSH_PERMISSION_FLAG = "push.permission.flag";


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");
        Boolean canPushed = redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);
        if(Boolean.FALSE.equals(canPushed)){
            log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
            return;
        }
    }
}
