package com.liyulin.demo.order.base.mapper;

import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.order.base.entity.OrderBillEntity;
import com.liyulin.demo.rpc.order.response.oms.OrderBillRespBody;

public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillRespBody, Long> {

}