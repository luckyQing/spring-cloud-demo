package com.liyulin.demo.controller.rpc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.dto.BasePageReq;
import com.liyulin.demo.common.dto.BasePageResp;
import com.liyulin.demo.common.dto.Req;
import com.liyulin.demo.common.dto.Resp;
import com.liyulin.demo.product.rpc.ProductInfoRpc;
import com.liyulin.demo.product.rpc.request.base.PageProductReqBody;
import com.liyulin.demo.product.rpc.response.base.ProductInfoRespBody;
import com.liyulin.demo.service.rpc.ProductRpcService;

@RestController
@Validated
public class ProductRpcController implements ProductInfoRpc {

	@Autowired
	private ProductRpcService productRpcService;

	@Override
	public Resp<BasePageResp<ProductInfoRespBody>> pageProduct(
			@RequestBody @Valid Req<@NotNull BasePageReq<PageProductReqBody>> req) {
		return new Resp<>(productRpcService.pageProduct(req.getBody()));
	}

}