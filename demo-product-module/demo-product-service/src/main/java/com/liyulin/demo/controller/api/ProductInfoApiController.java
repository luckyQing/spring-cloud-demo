package com.liyulin.demo.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.rpc.product.request.api.PageProductReqBody;
import com.liyulin.demo.rpc.product.response.api.PageProductRespBody;
import com.liyulin.demo.service.api.ProductInfoApiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/pass/product/productInfo")
@Validated
@Api(tags = "商品信息api相关接口")
public class ProductInfoApiController {

	@Autowired
	private ProductInfoApiService productService;

	@ApiOperation("分页查询商品信息")
	@PostMapping("pageProduct")
	public Resp<BasePageResp<PageProductRespBody>> pageProduct(
			@RequestBody @Valid Req<BasePageReq<PageProductReqBody>> req) {
		return RespUtil.success(productService.pageProduct(req.getBody()));
	}

}