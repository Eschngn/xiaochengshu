CREATE TABLE `t_channel`
(
    `id`          bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '频道名称',
    `create_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '逻辑删除(0：未删除 1：已删除)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='频道表';
