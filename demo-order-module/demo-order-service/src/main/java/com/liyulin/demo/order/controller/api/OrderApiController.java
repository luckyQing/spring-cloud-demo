package com.liyulin.demo.order.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.order.service.api.OrderApiService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("api/pass/order/order")
@Validated
@Api(tags = "订单api相关接口")
public class OrderApiController {
	
	@Autowired
	private OrderApiService orderApiService;
	
	// 创建订单

}