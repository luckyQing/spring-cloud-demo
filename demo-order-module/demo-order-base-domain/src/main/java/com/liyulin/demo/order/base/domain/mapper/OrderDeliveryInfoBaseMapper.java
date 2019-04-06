package com.liyulin.demo.order.base.domain.mapper;

import com.liyulin.demo.mybatis.mapper.ext.ExtMapper;
import com.liyulin.demo.order.base.domain.entity.OrderDeliveryInfoEntity;
import com.liyulin.demo.order.rpc.response.OrderDeliveryInfoRespBody;

public interface OrderDeliveryInfoBaseMapper
		extends ExtMapper<OrderDeliveryInfoEntity, OrderDeliveryInfoRespBody, Long> {

}