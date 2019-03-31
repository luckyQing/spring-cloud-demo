package com.liyulin.demo.order.base.domain.mapper;

import java.math.BigInteger;

import com.liyulin.demo.mybatis.mapper.ext.ExtMapper;
import com.liyulin.demo.order.base.domain.entity.OrderBillEntity;
import com.liyulin.demo.order.rpc.response.OrderBillRespBody;

public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillRespBody, BigInteger> {

}