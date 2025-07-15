CREATE TABLE `t_channel_topic_rel`
(
    `id`          bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `channel_id`  bigint(11) unsigned NOT NULL COMMENT '频道ID',
    `topic_id`    bigint(11) unsigned NOT NULL COMMENT '话题ID',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='频道-话题关联表';
