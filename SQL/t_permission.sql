CREATE TABLE `t_permission`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`      bigint unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
    `name`           varchar(16) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '权限名称',
    `type`           tinyint unsigned NOT NULL COMMENT '类型(1：目录 2：菜单 3：按钮)',
    `menu_url`       varchar(32) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '菜单路由',
    `menu_icon`      varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
    `sort`           int unsigned NOT NULL DEFAULT 0 COMMENT '管理系统中的显示顺序',
    `permission_key` varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '权限标识',
    `status`         tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态(0：启用；1：禁用)',
    `create_time`    datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`     bit(1)                                  NOT NULL DEFAULT b'0' COMMENT '逻辑删除(0：未删除 1：已删除)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';
