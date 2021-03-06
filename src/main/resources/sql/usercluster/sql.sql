CREATE TABLE `user_cluster_filter_group_1`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(250) NOT NULL COMMENT '一级分组名称',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
	`deleted_at` datetime(6) DEFAULT NULL COMMENT '删除时间',
	`created_by` VARCHAR (250) DEFAULT NULL COMMENT '创建用户',
	`updated_by` VARCHAR (250) DEFAULT NULL COMMENT '更新用户',
	`deleted_by` VARCHAR (250) DEFAULT NULL COMMENT '删除用户'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

INSERT INTO
	`user_cluster_filter_group_1`(`name`)
VALUES
	('登陆类'),
	('付费类'),
	('用户属性');

CREATE TABLE `user_cluster_filter_group_2`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(250) NOT NULL COMMENT '二级分组名称',
	`group_1_id` varchar(250) NOT NULL COMMENT '所属一级分组id',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
	`deleted_at` datetime(6) DEFAULT NULL COMMENT '删除时间',
	`created_by` VARCHAR (250) DEFAULT NULL COMMENT '创建用户',
	`updated_by` VARCHAR (250) DEFAULT NULL COMMENT '更新用户',
	`deleted_by` VARCHAR (250) DEFAULT NULL COMMENT '删除用户'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

INSERT INTO
	`user_cluster_filter_group_2`(`name`, `group_1_id`)
VALUES
	('注册用户', '1'),
	('新增用户', '1'),
	('活跃用户', '1'),
	('回流用户', '1'),
	('流失用户', '1'),
	('留存用户', '1'),
	('新充值用户', '2'),
	('充值用户', '2'),
	('非充值用户', '2'),
	('社会属性', '3');

CREATE TABLE `user_cluster_filter`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(250) NOT NULL COMMENT '条件名称',
	`group_2_id` varchar(250) NOT NULL COMMENT '所属二级分组id',
	`params_name` varchar(250) NOT NULL COMMENT '所需输入参数名称',
	`params_type` varchar(250) NOT NULL COMMENT '所需输入参数类型',
	`sql` varchar(250) NOT NULL COMMENT '该条件相应的查询SQL',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
	`deleted_at` datetime(6) DEFAULT NULL COMMENT '删除时间',
	`created_by` VARCHAR (250) DEFAULT NULL COMMENT '创建用户',
	`updated_by` VARCHAR (250) DEFAULT NULL COMMENT '更新用户',
	`deleted_by` VARCHAR (250) DEFAULT NULL COMMENT '删除用户'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

INSERT INTO
	`user_cluster_filter`(
		`name`,
		`group_2_id`,
		`params_name`,
		`params_type`,
		`sql`
	)
VALUES
	(
		'dateStart至dateEnd登陆过的用户',
		'3',
		'dateStart,dateEnd',
		'string,string',
		'date BETWEEN dateStart AND dateEnd'
	),
	(
		'dateStart至dateEnd登陆过的用户,并且当前最高等级在a至b级的用户',
		'3',
		'dateStart,dateEnd,aaa,bbb',
		'string,string,int,int',
		'date BETWEEN dateStart AND dateEnd AND level BETWEEN aaa AND bbb'
	);

CREATE TABLE `user_cluster`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`username` varchar(250) NOT NULL COMMENT '用户',
	`name` varchar(250) NOT NULL COMMENT '人群名称',
	`description` text DEFAULT NULL COMMENT '人群描述',
	`type` tinyint NOT NULL COMMENT '账号/角色/设备，0/1/2',
	`filters` text NOT NULL COMMENT '该人群所有过滤条件',
	`set_operation` varchar(20) NOT NULL COMMENT '集合操作',
	`share_with` varchar(255) NULL COMMENT '分享，他人可见',
	`sql` text NOT NULL COMMENT '该人群的最终查询SQL',
	`status` tinyint NOT NULL COMMENT '该人群的的计算状态 查询中/查询完成 0/1',
	`result` text NOT NULL COMMENT '该人群的最终查询结果',
	`size` bigint(20) NOT NULL COMMENT '该人群的大小',
	`created_at` varchar(19) DEFAULT NULL COMMENT '创建时间',
	`updated_at` varchar(19) DEFAULT NULL COMMENT '更新时间',
	`deleted_at` varchar(19) DEFAULT NULL COMMENT '删除时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user_cluster_track_task`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`user_cluster_id` bigint(20) NOT NULL COMMENT '分群ID',
	`date_start` varchar(19) DEFAULT NULL COMMENT '任务开始日期',
	`date_end` varchar(19) DEFAULT NULL COMMENT '任务结束日期',
	`status` tinyint NOT NULL COMMENT '任务状态 未开始/追踪中/已结束 0/1/2',
	`created_at` varchar(19) DEFAULT NULL COMMENT '创建时间',
	`updated_at` varchar(19) DEFAULT NULL COMMENT '更新时间',
	`deleted_at` varchar(19) DEFAULT NULL COMMENT '删除时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user_cluster_track_task_data`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`task_id` bigint(20) NOT NULL COMMENT '分群ID',
	`date` varchar(19) DEFAULT NULL COMMENT '日期',
	`day_login_cnt` double DEFAULT NULL COMMENT '活跃人数',
	`day_new_cnt` double DEFAULT NULL COMMENT '新增人数',
	`day_pay_money` double DEFAULT NULL COMMENT '充值金额',
	`day_pay_cnt` double DEFAULT NULL COMMENT '充值人数',
	`total_login_cnt` double DEFAULT NULL COMMENT '累计活跃人数',
	`total_new_cnt` double DEFAULT NULL COMMENT '累计新增人数',
	`total_pay_money` double DEFAULT NULL COMMENT '累计充值金额',
	`total_pay_cnt` double DEFAULT NULL COMMENT '累计充值人数',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
	`deleted_at` datetime(6) DEFAULT NULL COMMENT '删除时间',
	UNIQUE INDEX taskid_date (task_id, date)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user_cluster_track_task_data_retained_user`(
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
	`task_id` bigint(20) NOT NULL COMMENT '任务ID',
	`date_new_user` varchar(19) DEFAULT NULL COMMENT '观察新增用户日期',
	`new_cnt` double DEFAULT NULL COMMENT '新增人数',
	`date_lty` varchar(19) DEFAULT NULL COMMENT '计算留存日期',
	`total_pay_money` double DEFAULT NULL COMMENT '目标新增用户，累计收入',
	`ltv` double DEFAULT 0 COMMENT 'ltv',
	`created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
	`updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
	`deleted_at` datetime(6) DEFAULT NULL COMMENT '删除时间',
	UNIQUE INDEX taskid_date_ (task_id, date_new_user, date_retained_user)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;


SELECT
    ds,
        sum(case last_login_date when '2020-07-01' then 1 else 0 end) as dayLoginCnt,
        sum(case account_regdate when '2020-07-01' then 1 else 0 end) as dayNewCnt,
        sum(day_pay_money) as dayPayMoney,
        sum(case  when day_pay_times>0 then 1 else 0 end) as dayPayCnt,
        sum(case  when day_pay_times>0 and substr(first_pay_time,1,10)= '2020-07-01' then 1 else 0 end) as dayNewPayCnt,
        sum(case  when last_login_date >='2020-07-01'  then 1 else 0 end) as totalLoginCnt,
        sum(case when account_regdate >='2020-07-01'  then 1 else 0 end) as totalNewCnt,
        count(DISTINCT(case when last_pay_date>='2020-07-01' then account else null end)) as totalPayCnt
    
FROM
        data_analyze_label.dm_label_gamelog_kpi_account_ds
WHERE
        ds in ('20200701','20200702','20200703','20200704','20200705')
        AND op_id in('2145','2785','27775')
        AND account IN (
                SELECT
                        id
                FROM
                        db_temp.user_cluster_a06e0fb9_2d94_4c71_83ac_0d0ce9c1130f
                )
                
group by ds

 select date_add('day',1 ,CAST('2012-12-08' AS date))



SELECT
        date,
        sum(case first_login_date when date then 1 else 0 end) as dayNewCnt,
        sum(case last_login_date when date then 1 else 0 end) as dayLoginCnt,
        sum(day_pay_money) as dayPayMoney,
        sum(case  when day_pay_times>0 then 1 else 0 end) as dayPayCnt,
        sum(case  when day_pay_times>0 and substr(first_pay_time,1,10) = date then 1 else 0 end) as dayNewPayCnt,
        sum(case  when last_login_date >='2020-07-01'  then 1 else 0 end) as totalLoginCnt,
        sum(case  when first_login_date >='2020-07-01'  then 1 else 0 end) as totalNewCnt,
        count(DISTINCT(case when last_pay_date>='2020-07-01' then account else null end)) as totalPayCnt
FROM
        data_analyze_label.dm_label_gamelog_kpi_account_ds
WHERE
        ds in ('20200701','20200702','20200703','20200704')
        -- AND op_id in('2145','2785','27775')
        AND account IN (
                SELECT
                        id
                FROM
                        db_temp.user_cluster_a06e0fb9_2d94_4c71_83ac_0d0ce9c1130f
                )
                
group by date



select  op_id,first_login_date,
count(distinct pre_account ) as newCnt
,count(distinct case when login_1_times>0 then pre_account end ) ltv_1
,count(distinct case when login_2_times>0 then pre_account end ) ltv_2
,count(distinct case when login_3_times>0 then pre_account end ) ltv_3

from data_analyze_label.dm_label_gamelog_kpi_account_ds
where game_id=360 
and ds='20200813'
and op_id='2106'
and first_login_date>='2020-07-01'
and first_login_date<='2020-08-01'
group by op_id,first_login_date
order by first_login_date



SELECT
        first_login_date,
       count(*) as dayNewCnt,
       date,
      sum(case last_login_date when date then 1 else 0 end) as dayLoginCnt
       
       
FROM
        data_analyze_label.dm_label_gamelog_kpi_account_ds
WHERE
        ds in ('20200701','20200702','20200703','20200704','20200705','20200706','20200707','20200708','20200709','20200710')
        -- AND op_id in('2145','2785','27775')
        AND first_login_date>='2020-07-01'
        AND account IN (
                SELECT
                        id
                FROM
                        db_temp.user_cluster_a06e0fb9_2d94_4c71_83ac_0d0ce9c1130f
                )
                
group by ds,date,first_login_date
order by first_login_date,date
