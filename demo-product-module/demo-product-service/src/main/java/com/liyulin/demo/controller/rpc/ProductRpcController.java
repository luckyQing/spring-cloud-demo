package com.liyulin.demo.controller.rpc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.request.base.PageProductReqBody;
import com.liyulin.demo.rpc.product.response.base.ProductInfoRespBody;
import com.liyulin.demo.service.rpc.ProductRpcService;

@RestController
@Validated
public class ProductRpcController implements ProductInfoRpc {

	@Autowired
	private ProductRpcService productRpcService;

	@Override
	public Resp<BasePageResp<ProductInfoRespBody>> pageProduct(
			@RequestBody @Valid Req<BasePageReq<PageProductReqBody>> req) {
		return new Resp<>(productRpcService.pageProduct(req.getBody()));
	}

}