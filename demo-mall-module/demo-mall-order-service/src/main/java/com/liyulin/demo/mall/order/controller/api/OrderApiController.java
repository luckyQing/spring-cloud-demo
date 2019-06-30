package com.liyulin.demo.mall.order.controller.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.mall.order.exception.UpdateStockException;
import com.liyulin.demo.mall.order.service.api.OrderApiService;
import com.liyulin.demo.rpc.order.request.api.CreateOrderReqBody;
import com.liyulin.demo.rpc.order.response.api.CreateOrderRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/identity/order/order")
@Validated
@Api(tags = "订单api相关接口")
public class OrderApiController {

	@Autowired
	private OrderApiService orderApiService;

	@ApiOperation("创建订单")
	@PostMapping("create")
	public Resp<CreateOrderRespBody> create(@RequestBody @Valid Req<@NotNull CreateOrderReqBody> req)
			throws UpdateStockException {
		return orderApiService.create(req);
	}

}