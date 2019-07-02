package com.liyulin.demo.rpc.product;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.support.annotation.SmartFeignClient;
import com.liyulin.demo.rpc.constants.RpcConstants;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdsReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@SmartFeignClient(name = RpcConstants.Product.FEIGN_CLIENT_NAME)
@Api(tags = "商品信息rpc相关接口")
public interface ProductInfoRpc {

	@ApiOperation("根据id查询商品信息")
	@PostMapping("rpc/identity/product/productInfo/qryProductById")
	Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid QryProductByIdReqBody req);

	@ApiOperation("根据ids查询商品信息")
	@PostMapping("rpc/identity/product/productInfo/qryProductByIds")
	Resp<QryProductByIdsRespBody> qryProductByIds(@RequestBody @Valid QryProductByIdsReqBody req);

	@ApiOperation("更新库存")
	@PostMapping("rpc/identity/product/productInfo/updateStock")
	Resp<BaseDto> updateStock(@RequestBody @Valid @NotEmpty List<UpdateStockReqBody> req);

}