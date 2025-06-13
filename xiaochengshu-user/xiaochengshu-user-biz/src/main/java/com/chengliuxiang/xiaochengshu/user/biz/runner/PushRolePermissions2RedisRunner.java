package com.chengliuxiang.xiaochengshu.user.biz.runner;

import cn.hutool.core.collection.CollUtil;
import com.chengliuxiang.framework.common.utils.JsonUtil;
import com.chengliuxiang.xiaochengshu.user.biz.constant.RedisKeyConstants;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.PermissionDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.RoleDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.RolePermissionDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.PermissionDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.RoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.RolePermissionDOMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        try {
            Boolean canPushed = redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);
            if (Boolean.FALSE.equals(canPushed)) {
                log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
                return;
            }
            // 查询出所有启用的角色
            List<RoleDO> roleDOS = roleDOMapper.selectEnabledRoles();
            if (CollUtil.isNotEmpty(roleDOS)) {
                List<Long> roleIds = roleDOS.stream().map(RoleDO::getId).toList();
                List<RolePermissionDO> rolePermissionDOS = rolePermissionDOMapper.selectByRoleIds(roleIds);

                // 角色 ID —— 权限 IDs
                Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionDOS.stream()
                        .collect(Collectors.groupingBy(RolePermissionDO::getRoleId,
                                Collectors.mapping(RolePermissionDO::getPermissionId, Collectors.toList())));

                List<PermissionDO> permissionDOS = permissionDOMapper.selectAppEnabledList();

                // 权限 ID —— 权限 DO
                Map<Long, PermissionDO> permissionIdDOMap = permissionDOS.stream().collect(
                        Collectors.toMap(PermissionDO::getId, permissionDO -> permissionDO)
                );

                Map<String, List<String>> roleKeyPermissionsMap = Maps.newHashMap();

                // 循环所有角色
                roleDOS.forEach(roleDO -> {
                    Long roleId = roleDO.getId();
                    String roleKey = roleDO.getRoleKey();
                    List<Long> permissionIds = roleIdPermissionIdsMap.get(roleId);
                    if (CollUtil.isNotEmpty(permissionIds)) {
                        List<String> permissionKeys= Lists.newArrayList();
                        permissionIds.forEach(permissionId -> {
                            PermissionDO permissionDO = permissionIdDOMap.get(permissionId);
                            permissionKeys.add(permissionDO.getPermissionKey());
                        });
                        roleKeyPermissionsMap.put(roleKey, permissionKeys);
                    }
                });

                // 同步至 Redis 中，方便后续网关查询鉴权使用
                roleKeyPermissionsMap.forEach((roleKey, permissions) -> {
                    String key = RedisKeyConstants.buildRolePermissionsKey(roleKey);
                    redisTemplate.opsForValue().set(key, JsonUtil.toJsonString(permissions));
                });
                log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
            }
        } catch (Exception e) {
            redisTemplate.delete(PUSH_PERMISSION_FLAG);
            log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
        }
    }
}
