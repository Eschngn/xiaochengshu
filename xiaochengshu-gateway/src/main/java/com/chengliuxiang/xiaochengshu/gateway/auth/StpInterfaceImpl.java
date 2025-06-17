package com.chengliuxiang.xiaochengshu.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.chengliuxiang.xiaochengshu.gateway.constant.RedisKeyConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 获取用户的权限数据集合
     *
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    @SneakyThrows
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        log.info("## 获取用户权限列表, loginId: {}", loginId);

        String userRolesKey = RedisKeyConstants.buildUserRoleKey(Long.valueOf(loginId.toString()));

        // 根据用户 ID 从 Redis 中获取该用户所有的角色数据集合
        // Redis 中数据格式 user:roles:4231123 —— "[\"common_user\"]"
        String userRolesKeysValue = redisTemplate.opsForValue().get(userRolesKey);

        if (Objects.isNull(userRolesKeysValue)) {
            return null;
        }

        List<String> userRoleKeysList = objectMapper.readValue(userRolesKeysValue, new TypeReference<>() {
        });
        if (CollUtil.isNotEmpty(userRoleKeysList)) {
            // 构建角色-权限 Redis Key 集合
            List<String> rolePermissionKeys = userRoleKeysList.stream()
                    .map(RedisKeyConstants::buildRolePermissionsKey).toList();
            // 根据该用户的所有角色数据，从 Redis 中获取每个角色下对应的权限数据
            // Redis 中数据格式 role:permissions:common_user —— "[\"app:note:publish\",\"app:comment:publish\"]"
            List<String> rolePermissionValues = redisTemplate.opsForValue().multiGet(rolePermissionKeys);
            if (CollUtil.isNotEmpty(rolePermissionValues)) {
                List<String> permissions = Lists.newArrayList();
                rolePermissionValues.forEach(jsonValue -> {
                    try {
                        // 将 Redis 中存储的权限 JSON 数据转换为 List<String>
                        List<String> rolePermissionsList = objectMapper.readValue(jsonValue, new TypeReference<>() {
                        });
                        permissions.addAll(rolePermissionsList);
                    } catch (JsonProcessingException e) {
                        log.error("===> JSON 解析错误:", e);
                    }
                });
                return permissions;
            }
        }
        return null;
    }


    /**
     * 获取用户的角色数据集合
     *
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    @SneakyThrows
    public List<String> getRoleList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的角色列表

        String userRolesKeysValue = RedisKeyConstants.buildUserRoleKey(Long.valueOf(loginId.toString()));

        return objectMapper.readValue(userRolesKeysValue, new TypeReference<>() {
        });
    }
}
