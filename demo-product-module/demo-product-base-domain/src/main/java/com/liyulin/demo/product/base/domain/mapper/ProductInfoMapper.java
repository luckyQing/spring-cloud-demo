package com.liyulin.demo.product.base.domain.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.liyulin.demo.mybatis.mapper.ext.ExtMapper;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;

@Mapper
public interface ProductInfoMapper extends ExtMapper<ProductInfoEntity> {

}