package com.liyulin.demo.order.base.domain.mapper;

import com.liyulin.demo.mybatis.mapper.ext.ExtMapper;
import com.liyulin.demo.order.base.domain.entity.OrderBillEntity;
import com.liyulin.demo.rpc.order.response.base.OrderBillRespBody;

public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillRespBody, Long> {

}