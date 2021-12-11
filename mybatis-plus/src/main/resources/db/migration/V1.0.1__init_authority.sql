-- 创建测试表
CREATE TABLE `user`
(
    `id`             bigint UNSIGNED NOT NULL COMMENT '主键',
    `name`           varchar(255) NULL COMMENT '姓名',
    `age`            int NULL COMMENT '年龄',
    `address`        varchar(255) NULL COMMENT '地址',
    `create_time`    datetime NULL COMMENT '创建时间',
    `update_time`    datetime NULL COMMENT '修改时间',
    `create_user_id` bigint UNSIGNED NULL COMMENT '创建人id',
    `update_user_id` bigint UNSIGNED NULL COMMENT '修改人id',
    `deleted`        bit(1) NULL COMMENT '删除状态',
    PRIMARY KEY (`id`)
);

ALTER TABLE `user`
    MODIFY COLUMN `create_time` datetime(0) NOT NULL COMMENT '创建时间' AFTER `address`,
    MODIFY COLUMN `update_time` datetime(0) NOT NULL COMMENT '修改时间' AFTER `create_time`,
    MODIFY COLUMN `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id' AFTER `update_time`,
    MODIFY COLUMN `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '修改人id' AFTER `create_user_id`,
    MODIFY COLUMN `deleted` bit(1) NOT NULL DEFAULT 0 COMMENT '删除状态' AFTER `update_user_id`;

INSERT INTO `user`(`id`, `name`, `age`, `address`, `create_time`, `update_time`, `create_user_id`, `update_user_id`,
                   `deleted`)
VALUES (1, '张三', 18, '重庆万州', '2021-12-11 15:19:09', '2021-12-11 15:19:12', 1, 1, b'0');
INSERT INTO `user`(`id`, `name`, `age`, `address`, `create_time`, `update_time`, `create_user_id`, `update_user_id`,
                   `deleted`)
VALUES (2, '李四', 20, '重庆渝北', '2021-12-11 15:19:36', '2021-12-11 15:19:39', 1, 1, b'0');
