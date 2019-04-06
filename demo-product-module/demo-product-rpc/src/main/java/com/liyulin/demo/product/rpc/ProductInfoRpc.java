package com.liyulin.demo.product.rpc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.web.openfeign.condition.OnFeignClientCondition;
import com.liyulin.demo.product.rpc.request.base.PageProductReqBody;
import com.liyulin.demo.product.rpc.response.base.ProductInfoRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Conditional(OnFeignClientCondition.class)
@FeignClient
@Api(tags = "商品信息rpc相关接口")
public interface ProductInfoRpc {

	@ApiOperation("分页查询商品信息")
	@PostMapping("rpc/pass/product/productInfo/pageProduct")
	Resp<BasePageResp<ProductInfoRespBody>> pageProduct(
			@RequestBody @Valid Req<com.liyulin.demo.common.business.dto.BasePageReq<PageProductReqBody>> req);

}