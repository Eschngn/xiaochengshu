CREATE TABLE `t_role`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_name`   varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名',
    `role_key`    varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色唯一标识',
    `status`      tinyint                                NOT NULL DEFAULT '0' COMMENT '状态(0：启用 1：禁用)',
    `sort`        int unsigned NOT NULL DEFAULT 0 COMMENT '管理系统中的显示顺序',
    `remark`      varchar(255) COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '备注',
    `create_time` datetime                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
    `is_deleted`  bit(1)                                 NOT NULL DEFAULT b'0' COMMENT '逻辑删除(0：未删除 1：已删除)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';
