package com.liyulin.demo.controller.oms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.product.rpc.request.oms.ProductInsertReqBody;
import com.liyulin.demo.service.oms.ProductOmsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("oms/auth/product/productInfo")
@Validated
@Api(tags = "商品信息oms相关接口")
public class ProductOmsController {
	
	@Autowired
	private ProductOmsService productOmsService;

	@ApiOperation("新增商品信息")
	@PostMapping("create")
	public Resp<BaseDto> create(@RequestBody @Valid Req<@NotNull ProductInsertReqBody> req) {
		return productOmsService.create(req.getBody());
	}

}