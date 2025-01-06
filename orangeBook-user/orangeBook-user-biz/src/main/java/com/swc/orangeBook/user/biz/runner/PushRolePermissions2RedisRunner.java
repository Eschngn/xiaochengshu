package com.swc.orangeBook.user.biz.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.swc.framework.common.util.JsonUtil;

import com.swc.orangeBook.user.biz.constant.RedisKeyConstants;
import com.swc.orangeBook.user.biz.domain.dataobject.PermissionDO;
import com.swc.orangeBook.user.biz.domain.dataobject.RoleDO;
import com.swc.orangeBook.user.biz.domain.dataobject.RolePermissionDO;
import com.swc.orangeBook.user.biz.domain.mapper.PermissionDOMapper;
import com.swc.orangeBook.user.biz.domain.mapper.RoleDOMapper;
import com.swc.orangeBook.user.biz.domain.mapper.RolePermissionDOMapper;
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


/**
 * @author Wilson
 * @Description: 推送角色权限数据到 Redis 中
 * @date 2024/9/13 22:35
 */
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

    // 权限同步标记 Key
    private static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");
        try {
            // 是否能够同步数据: 原子操作，只有在键 PUSH_PERMISSION_FLAG 不存在时，才会设置该键的值为 "1"，并设置过期时间为 1 天
            Boolean canPushed  = redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);

            // 如果无法同步权限数据
            if (!canPushed) {
                log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
                return;
            }
            //目的:将每个角色及其对应的权限数据存到redis中,key为角色id,value为对应该角色的所有权限数据

            //逻辑:查询出所有的角色,一个角色可能会对应多个权限,所以要遍历每个角色的角色id去角色权限表中拿到每个角色对应的所有角色权限关系集合
            //再将角色权限关系集合转换成一个map1,key为角色id,value为对应的权限id集合    <roleId,permissionIds>
            //再去权限表中拿到所有启用的权限集合,将该集合利用stream流转换为map2,key为权限id,value为对应的权限   <permissionId,permissionDO>
            //new一个HashMap,key为角色id,value为对用角色的权限数据集合
            //遍历每一个角色,根据角色id从map1中拿到对应的权限id集合
            //再遍历每个权限id,根据权限id从map2中拿到对应的权限数据存到一个list集合中,最后将该list集合存到HashMap对应的角色中 <roleId,permissionDOs>

            //查询出所有的角色
            List<RoleDO> roleDOS = roleDOMapper.selectEnabledList();
            if(CollUtil.isNotEmpty(roleDOS)){
                //拿到所有角色的ID
                List<Long> roleIds = roleDOS.stream().map(RoleDO::getId).toList();
                // 根据角色 ID, 批量查询出所有角色对应的权限
                List<RolePermissionDO> rolePermissionDOS = rolePermissionDOMapper.selectByRoleIds(roleIds);

                //按角色 ID 分组, 每个角色 ID 对应多个权限 ID
                Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionDOS.stream().collect(
                        Collectors.groupingBy(RolePermissionDO::getRoleId,
                                Collectors.mapping(RolePermissionDO::getPermissionId, Collectors.toList()))
                );
                List<PermissionDO> permissionDOS = permissionDOMapper.selectAppEnabledList();

                //权限 ID - 权限 DO
                Map<Long, PermissionDO> permissionIdDOMap = permissionDOS.stream().collect(
                        Collectors.toMap(PermissionDO::getId, permissionDO -> permissionDO)
                );
                // 组织 角色ID-权限 关系
                Map<String, List<String>> roleKeyPermissionsMap = Maps.newHashMap();

                // 循环所有角色
                roleDOS.forEach(roleDO -> {
                    // 当前角色 ID
                    Long roleId = roleDO.getId();
                    //当前角色 roleKey
                    String roleKey = roleDO.getRoleKey();
                    // 当前角色 ID 对应的权限 ID 集合
                    List<Long> permissionIds = roleIdPermissionIdsMap.get(roleId);
                    if(CollUtil.isNotEmpty(permissionIds)) {
                        List<String> permissionKeys=Lists.newArrayList();
                        List<PermissionDO> perDOS = Lists.newArrayList();
                        permissionIds.forEach(permissionId -> {
                            // 根据权限 ID 获取具体的权限 DO 对象
                            PermissionDO permissionDO = permissionIdDOMap.get(permissionId);
                            perDOS.add(permissionDO);
                            permissionKeys.add(permissionDO.getPermissionKey());
                        });
                        roleKeyPermissionsMap.put(roleKey, permissionKeys);
                    }
                });
                // 同步至 Redis 中，方便后续网关查询鉴权使用
                roleKeyPermissionsMap.forEach((roleKey,permissions)->{
                    String key = RedisKeyConstants.buildRolePermissionsKey(roleKey);
                    redisTemplate.opsForValue().set(key, JsonUtil.toJsonString(permissions));
                });
            }
            log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
        }catch (Exception e){
            redisTemplate.delete(PUSH_PERMISSION_FLAG);
            log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
        }


    }
}
