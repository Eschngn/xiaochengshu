package com.chengliuxiang.xiaochengshu.user.biz.constant;

public class RedisKeyConstants {
    /**
     * 角色对应的权限集合 KEY 前缀
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     * 用户角色数据 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    private static final String USER_INFO_KEY_PREFIX = "user:info:";


    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }

    public static String buildUserRolesKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

    public static String buildUserInfoKey(Long userId) {
        return USER_INFO_KEY_PREFIX + userId;
    }
}
