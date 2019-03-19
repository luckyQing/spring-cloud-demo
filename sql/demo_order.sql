CREATE TABLE `t_order_bill` (
	`f_id` BIGINT(20) UNSIGNED NOT NULL,
	`f_amount` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单金额总金额',
	`f_pay_state` TINYINT(1) UNSIGNED NOT NULL COMMENT '支付状态（1：待支付；2：支付成功；3：支付失败）',
	`f_refund_state` TINYINT(1) UNSIGNED NOT NULL COMMENT '退款状态（1：无需退款；2：待退款；3：退款失败；4：退款成功）',
	`f_buyer` BIGINT(20) UNSIGNED NOT NULL COMMENT '购买人id（demo_user库t_user_info表f_id）',
	`f_sys_add_time` DATETIME NOT NULL COMMENT '创建时间',
	`f_sys_upd_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`f_sys_del_time` DATETIME NULL DEFAULT NULL COMMENT '删除时间',
	`f_sys_add_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '新增者',
	`f_sys_upd_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
	`f_sys_del_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '删除者',
	`f_sys_del_state` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '记录状态=={\'1\':\'正常\',\'2\':\'已删除\'}',
	PRIMARY KEY (`f_id`)
)
COMMENT='订单信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


CREATE TABLE `t_order_delivery_info` (
	`f_id` BIGINT(20) UNSIGNED NOT NULL,
	`t_order_bill_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '所属订单（t_order_bill表f_id）',
	`t_product_info_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '购买的商品id（demo_product库t_product_info表f_id）',
	`f_product_name` VARCHAR(120) NOT NULL COMMENT '商品名称',
	`f_price` BIGINT(20) UNSIGNED NOT NULL COMMENT '商品购买价格（单位：万分之一元）',
	`f_buy_count` INT(8) UNSIGNED NOT NULL COMMENT '购买数量',
	`f_sys_add_time` DATETIME NOT NULL COMMENT '创建时间',
	`f_sys_upd_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`f_sys_del_time` DATETIME NULL DEFAULT NULL COMMENT '删除时间',
	`f_sys_add_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '新增者',
	`f_sys_upd_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
	`f_sys_del_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '删除者',
	`f_sys_del_state` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '记录状态=={\'1\':\'正常\',\'2\':\'已删除\'}',
	PRIMARY KEY (`f_id`)
)
COMMENT='运单信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;