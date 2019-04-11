package com.liyulin.demo.mall.order.mapper.base;

import org.apache.ibatis.annotations.Mapper;

import com.liyulin.demo.mall.order.entity.OrderDeliveryInfoEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.order.response.oms.OrderDeliveryInfoRespBody;

@Mapper
public interface OrderDeliveryInfoBaseMapper
		extends ExtMapper<OrderDeliveryInfoEntity, OrderDeliveryInfoRespBody, Long> {

}