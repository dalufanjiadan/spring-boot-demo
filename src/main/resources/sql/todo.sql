CREATE TABLE `todo`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`username` varchar(50) NOT NULL COMMENT '用户名',
	`description` varchar(200) NOT NULL COMMENT 'todo 内容描述',
	`status` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'todo 状态 未完成：0 完成：1 所有：2',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;