package com.liyulin.demo.mall.order.mapper.base;

import com.liyulin.demo.mall.order.entity.base.OrderBillEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.order.response.base.OrderBillBaseRespBody;

public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillBaseRespBody, Long> {

}