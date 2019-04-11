package com.liyulin.demo.mall.product.controller.rpc;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqObjectBody;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.product.service.rpc.ProductInfoRpcService;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;

@RestController
@Validated
public class ProductInfoRpcController implements ProductInfoRpc {

	@Autowired
	private ProductInfoRpcService productRpcService;

	@Override
	public Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid Req<@NotNull QryProductByIdReqBody> req) {
		return RespUtil.success(productRpcService.qryProductById(req.getBody()));
	}

	@Override
	public Resp<BaseDto> updateStock(
			@RequestBody @Valid Req<@NotNull ReqObjectBody<@NotEmpty List<UpdateStockReqBody>>> req) {
		return productRpcService.updateStock(req);
	}

}