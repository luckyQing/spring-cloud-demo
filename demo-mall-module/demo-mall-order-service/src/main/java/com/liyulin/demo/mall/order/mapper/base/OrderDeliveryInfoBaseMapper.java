package com.liyulin.demo.mall.order.mapper.base;

import com.liyulin.demo.mall.order.entity.base.OrderDeliveryInfoEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.order.response.base.OrderDeliveryInfoBaseRespBody;

public interface OrderDeliveryInfoBaseMapper
		extends ExtMapper<OrderDeliveryInfoEntity, OrderDeliveryInfoBaseRespBody, Long> {

}