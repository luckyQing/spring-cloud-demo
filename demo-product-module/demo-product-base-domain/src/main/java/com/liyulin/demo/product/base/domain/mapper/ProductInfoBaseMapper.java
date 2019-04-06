package com.liyulin.demo.product.base.domain.mapper;

import com.liyulin.demo.mybatis.mapper.ext.ExtMapper;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.rpc.product.response.base.ProductInfoRespBody;

public interface ProductInfoBaseMapper extends ExtMapper<ProductInfoEntity, ProductInfoRespBody, Long> {

}