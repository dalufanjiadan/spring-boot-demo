CREATE TABLE `user`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`username` varchar(250) NOT NULL COMMENT '用户名',
	`password` varchar(250) NOT NULL COMMENT '密码',
	`phone` varchar(11) NOT NULL COMMENT '电话号码',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
	`deleted_at` datetime(6) DEFAULT NULL COMMENT '删除时间',
	`created_by` VARCHAR (250) DEFAULT NULL COMMENT '创建用户',
	`updated_by` VARCHAR (250) DEFAULT NULL COMMENT '更新用户',
	`deleted_by` VARCHAR (250) DEFAULT NULL COMMENT '删除用户'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user_cluster_tag_group_1`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(250) NOT NULL COMMENT '一级分组名称',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
	`deleted_at` datetime(6) DEFAULT NULL COMMENT '删除时间',
	`created_by` VARCHAR (250) DEFAULT NULL COMMENT '创建用户',
	`updated_by` VARCHAR (250) DEFAULT NULL COMMENT '更新用户',
	`deleted_by` VARCHAR (250) DEFAULT NULL COMMENT '删除用户'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;


