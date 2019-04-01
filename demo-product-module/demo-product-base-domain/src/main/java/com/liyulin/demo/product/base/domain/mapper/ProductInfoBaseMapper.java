package com.liyulin.demo.product.base.domain.mapper;

import java.math.BigInteger;

import com.liyulin.demo.mybatis.mapper.ext.ExtMapper;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.product.rpc.response.base.ProductInfoRespBody;

public interface ProductInfoBaseMapper extends ExtMapper<ProductInfoEntity, ProductInfoRespBody, BigInteger> {

}