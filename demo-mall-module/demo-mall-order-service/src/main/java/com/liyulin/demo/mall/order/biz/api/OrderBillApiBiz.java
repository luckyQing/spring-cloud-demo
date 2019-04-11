package com.liyulin.demo.mall.order.biz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.mall.order.entity.OrderBillEntity;
import com.liyulin.demo.mall.order.mapper.base.OrderBillBaseMapper;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;

/**
 * 订单信息api biz
 *
 * @author liyulin
 * @date 2019年4月8日上午12:53:39
 */
@Repository
public class OrderBillApiBiz extends BaseBiz<OrderBillEntity>{

	@Autowired
	private OrderBillBaseMapper orderBillBaseMapper;
	
}