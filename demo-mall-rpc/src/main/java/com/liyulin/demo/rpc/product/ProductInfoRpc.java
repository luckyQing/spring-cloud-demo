package com.liyulin.demo.rpc.product;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqObjectBody;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.web.openfeign.condition.OnFeignClientCondition;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Conditional(OnFeignClientCondition.class)
@FeignClient
@Api(tags = "商品信息rpc相关接口")
public interface ProductInfoRpc {

	@ApiOperation("分页查询商品信息")
	@PostMapping("rpc/pass/product/productInfo/qryProductById")
	Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid Req<@NotNull QryProductByIdReqBody> req);

	@ApiOperation("更新库存")
	@PostMapping("rpc/pass/product/productInfo/updateStock")
	Resp<BaseDto> updateStock(@RequestBody @Valid Req<@NotNull ReqObjectBody<@NotEmpty List<UpdateStockReqBody>>> req);

}