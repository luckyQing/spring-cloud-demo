package com.liyulin.demo.mall.product.controller.oms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.product.service.oms.ProductInfoOmsService;
import com.liyulin.demo.rpc.product.request.oms.PageProductReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductDeleteReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductInsertReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductUpdateReqBody;
import com.liyulin.demo.rpc.product.response.oms.ProductInfoRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("oms/auth/product/productInfo")
@Validated
@Api(tags = "商品信息oms相关接口")
public class ProductInfoOmsController {

	@Autowired
	private ProductInfoOmsService productOmsService;

	@ApiOperation("新增商品信息")
	@PostMapping("create")
	public Resp<BaseDto> create(@RequestBody @Valid Req<@NotNull ProductInsertReqBody> req) {
		return productOmsService.create(req.getBody());
	}

	@ApiOperation("修改商品信息")
	@PostMapping("update")
	public Resp<BaseDto> update(@RequestBody @Valid Req<@NotNull ProductUpdateReqBody> req) {
		return productOmsService.update(req.getBody());
	}

	@ApiOperation("逻辑删除商品")
	@PostMapping("logicDelete")
	public Resp<BaseDto> logicDelete(@RequestBody @Valid Req<@NotNull ProductDeleteReqBody> req) {
		return productOmsService.logicDelete(req.getBody());
	}

	@ApiOperation("分页查询商品信息")
	@PostMapping("pageProduct")
	public Resp<BasePageResp<ProductInfoRespBody>> pageProduct(
			@RequestBody @Valid Req<@NotNull BasePageReq<PageProductReqBody>> req) {
		return RespUtil.success(productOmsService.pageProduct(req.getBody()));
	}

}