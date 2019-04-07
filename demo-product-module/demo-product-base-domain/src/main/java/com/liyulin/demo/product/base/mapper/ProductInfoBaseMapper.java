package com.liyulin.demo.product.base.mapper;

import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.product.base.entity.ProductInfoEntity;
import com.liyulin.demo.rpc.product.response.oms.ProductInfoRespBody;

public interface ProductInfoBaseMapper extends ExtMapper<ProductInfoEntity, ProductInfoRespBody, Long> {

}