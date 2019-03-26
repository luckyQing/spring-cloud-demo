package com.liyulin.demo.product.base.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Conditional;

import com.liyulin.demo.common.web.openfeign.condition.OnFeignClientCondition;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@Conditional(OnFeignClientCondition.class)
@FeignClient
@Api(tags = "商品信息rpc相关接口")
@ApiIgnore
public interface ProductInfoRpc {

}