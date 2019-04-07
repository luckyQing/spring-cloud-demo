package com.liyulin.demo.order.base.mapper;

import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.order.base.entity.OrderDeliveryInfoEntity;
import com.liyulin.demo.rpc.order.response.oms.OrderDeliveryInfoRespBody;

public interface OrderDeliveryInfoBaseMapper
		extends ExtMapper<OrderDeliveryInfoEntity, OrderDeliveryInfoRespBody, Long> {

}