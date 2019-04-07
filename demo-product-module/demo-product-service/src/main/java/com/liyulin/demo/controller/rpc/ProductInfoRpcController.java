package com.liyulin.demo.controller.rpc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.service.rpc.ProductInfoRpcService;

@RestController
@Validated
public class ProductInfoRpcController implements ProductInfoRpc {

	@Autowired
	private ProductInfoRpcService productRpcService;

	@Override
	public Resp<QryProductByIdRespBody> qryProductById(@RequestBody @Valid Req<@NotNull QryProductByIdReqBody> req) {
		return RespUtil.success(productRpcService.qryProductById(req.getBody()));
	}

}