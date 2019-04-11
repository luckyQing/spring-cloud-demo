package com.liyulin.demo.mall.product.mapper.base;

import org.apache.ibatis.annotations.Mapper;

import com.liyulin.demo.mall.product.entity.base.ProductInfoEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.product.response.oms.ProductInfoRespBody;

@Mapper
public interface ProductInfoBaseMapper extends ExtMapper<ProductInfoEntity, ProductInfoRespBody, Long> {

}