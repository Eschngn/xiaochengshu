package com.swc.orangebook.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.swc.orangebook.gateway.constant.RedisKeyConstants;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.List;

/**
 * @author Wilson
 * @Description: 自定义权限验证接口扩展
 * @date 2024/9/15 12:31
 */
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 获取用户权限集合,包含该用户的每一条permissionKey
     * @param loginId
     * @param loginType
     * @return
     */
    @SneakyThrows
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        log.info("## 获取用户权限列表, loginId: {}", loginId);

        // 构建 用户-角色 Redis Key
        String userRolesKey  = RedisKeyConstants.buildUserRoleKey(Long.valueOf(loginId.toString()));

        // 根据用户 ID ，从 Redis 中获取该用户的角色集合
        String useRolesValue = redisTemplate.opsForValue().get(userRolesKey);

        if (StringUtils.isBlank(useRolesValue)) {
            return null;
        }
        // 将 JSON 字符串转换为 List<String> 角色集合
        List<String> userRoleKeys = objectMapper.readValue(useRolesValue, new TypeReference<>() {});

        if(CollUtil.isNotEmpty(userRoleKeys)){
            // 查询这些角色对应的权限
            // 构建 角色-权限 Redis Key 集合
            List<String> rolePermissionsKeys  = userRoleKeys.stream()
                    .map(RedisKeyConstants::buildRolePermissionsKey).toList();
            // 通过 key 集合批量查询权限，提升查询性，查出的是对应roleKey的所有permissionKey的集合
            // 这里面每一条String可能包含多条permissionKey(对应一个roleKey)
            List<String> rolePermissionsValues = redisTemplate.opsForValue().multiGet(rolePermissionsKeys);
            if(CollUtil.isNotEmpty(rolePermissionsValues)){
                List<String> permissions = Lists.newArrayList();
                rolePermissionsValues.forEach(jsonValue->{
                    try {
                        // 将 JSON 字符串转换为 List<String> 权限集合
                        List<String> rolePermissions = objectMapper.readValue(jsonValue, new TypeReference<>() {});
                        permissions.addAll(rolePermissions);
                    }catch (JsonProcessingException e){
                        log.error("==> JSON 解析错误: ", e);
                    }
                });
                // 返回此用户所拥有的权限
                return permissions;
            }
        }
        return null;
    }

    /**
     * 获取用户角色集合
     * @param loginId
     * @param loginType
     * @return
     */
    @SneakyThrows
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的角色列表
        log.info("## 获取用户角色列表, loginId: {}", loginId);
        // 构建 用户-角色 Redis Key
        String userRolesKey  = RedisKeyConstants.buildUserRoleKey(Long.valueOf(loginId.toString()));

        // 根据用户 ID ，从 Redis 中获取该用户的角色集合
        String useRolesValue = redisTemplate.opsForValue().get(userRolesKey);

        if(StringUtils.isBlank(useRolesValue)){
            return null;
        }
        // 将 JSON 字符串转换为 List<String> 集合
        return objectMapper.readValue(useRolesValue,new TypeReference<>(){});
    }
}
