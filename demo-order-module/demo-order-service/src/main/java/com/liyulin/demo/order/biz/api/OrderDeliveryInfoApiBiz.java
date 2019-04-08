package com.liyulin.demo.order.biz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.mybatis.common.biz.BaseBiz;
import com.liyulin.demo.order.base.entity.OrderDeliveryInfoEntity;
import com.liyulin.demo.order.base.mapper.OrderDeliveryInfoBaseMapper;

/**
 * 运单信息api biz
 *
 * @author liyulin
 * @date 2019年4月8日上午12:53:39
 */
@Repository
public class OrderDeliveryInfoApiBiz extends BaseBiz<OrderDeliveryInfoEntity> {

	@Autowired
	private OrderDeliveryInfoBaseMapper orderDeliveryInfoBaseMapper;

}