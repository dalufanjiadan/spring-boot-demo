CREATE TABLE `file`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(50) NOT NULL COMMENT '文件名',
	`type` varchar(50) NOT NULL COMMENT '文件类型',
	`data` blob NOT NULL COMMENT 'content',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;