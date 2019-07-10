package com.liyulin.demo.mall.order.biz.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.mall.order.entity.base.OrderDeliveryInfoEntity;
import com.liyulin.demo.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;

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

	public boolean create(List<OrderDeliveryInfoEntity> entities) {
		return orderDeliveryInfoBaseMapper.insertList(entities) > 0;
	}

}