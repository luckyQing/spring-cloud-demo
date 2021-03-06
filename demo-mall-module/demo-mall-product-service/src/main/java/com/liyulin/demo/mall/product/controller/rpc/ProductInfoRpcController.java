package com.liyulin.demo.mall.product.controller.rpc;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.product.service.rpc.ProductInfoRpcService;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdsReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

@RestController
@Validated
public class ProductInfoRpcController implements ProductInfoRpc {

	@Autowired
	private ProductInfoRpcService productInfoRpcService;

	@Override
	public Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid QryProductByIdReqBody req) {
		return RespUtil.success(productInfoRpcService.qryProductById(req));
	}

	@Override
	public Resp<QryProductByIdsRespBody> qryProductByIds(@RequestBody @Valid QryProductByIdsReqBody req) {
		return RespUtil.success(productInfoRpcService.qryProductByIds(req));
	}

	@Override
	public Resp<BaseDto> updateStock(@RequestBody @Valid @NotEmpty List<UpdateStockReqBody> req) {
		return productInfoRpcService.updateStock(req);
	}

}