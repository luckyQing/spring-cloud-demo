package com.liyulin.demo.mall.order.mapper.base;

import org.apache.ibatis.annotations.Mapper;

import com.liyulin.demo.mall.order.entity.base.OrderBillEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.order.response.oms.OrderBillRespBody;

@Mapper
public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillRespBody, Long> {

}