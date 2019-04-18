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
import com.liyulin.demo.common.condition.OnFeignClientCondition;
import com.liyulin.demo.rpc.constants.RpcConstants;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdsReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@ConditionalFeignClient(value = RpcConstants.Product.FEIGN_CLIENT_NAME)
@FeignClient(RpcConstants.Product.FEIGN_CLIENT_NAME)
@Conditional(OnFeignClientCondition.class)
@Api(tags = "商品信息rpc相关接口")
public interface ProductInfoRpc {

	@ApiOperation("根据id查询商品信息")
	@PostMapping("rpc/pass/product/productInfo/qryProductById")
	Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid Req<@NotNull QryProductByIdReqBody> req);

	@ApiOperation("根据ids查询商品信息")
	@PostMapping("rpc/pass/product/productInfo/qryProductByIds")
	Resp<QryProductByIdsRespBody> qryProductByIds(@RequestBody @Valid Req<@NotNull QryProductByIdsReqBody> req);

	@ApiOperation("更新库存")
	@PostMapping("rpc/pass/product/productInfo/updateStock")
	Resp<BaseDto> updateStock(@RequestBody @Valid Req<@NotNull ReqObjectBody<@NotEmpty List<UpdateStockReqBody>>> req);

}