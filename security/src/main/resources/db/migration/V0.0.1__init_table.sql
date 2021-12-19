DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`             bigint       NOT NULL,
    `username`       varchar(255) NOT NULL COMMENT '用户名',
    `password`       varchar(255) NOT NULL COMMENT '密码',
    `create_user_id` bigint       NOT NULL COMMENT '创建人id',
    `update_user_id` bigint       NOT NULL COMMENT '更新人id',
    `create_time`    datetime     NOT NULL COMMENT '创建时间',
    `update_time`    datetime     NOT NULL COMMENT '更新时间',
    `deleted`        bit(1)       NOT NULL COMMENT '是否被删除',
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`             bigint       NOT NULL,
    `role_name`      varchar(255) NOT NULL COMMENT '角色名',
    `create_user_id` bigint       NOT NULL COMMENT '创建人id',
    `update_user_id` bigint       NOT NULL COMMENT '更新人id',
    `create_time`    datetime     NOT NULL COMMENT '创建时间',
    `update_time`    datetime     NOT NULL COMMENT '更新时间',
    `deleted`        bit(1)       NOT NULL COMMENT '是否被删除',
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`             bigint       NOT NULL,
    `menu`           varchar(255) NOT NULL COMMENT '角色名',
    `parent_id`      bigint NULL COMMENT '父id',
    `create_user_id` bigint       NOT NULL COMMENT '创建人id',
    `update_user_id` bigint       NOT NULL COMMENT '更新人id',
    `create_time`    datetime     NOT NULL COMMENT '创建时间',
    `update_time`    datetime     NOT NULL COMMENT '更新时间',
    `deleted`        bit(1)       NOT NULL COMMENT '是否被删除',
    PRIMARY KEY (`id`)
) COMMENT = '菜单';