package com.liyulin.demo.mall.product.mapper.base;

import com.liyulin.demo.mall.product.entity.base.ProductInfoEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.product.response.base.ProductInfoBaseRespBody;

public interface ProductInfoBaseMapper extends ExtMapper<ProductInfoEntity, ProductInfoBaseRespBody, Long> {

}