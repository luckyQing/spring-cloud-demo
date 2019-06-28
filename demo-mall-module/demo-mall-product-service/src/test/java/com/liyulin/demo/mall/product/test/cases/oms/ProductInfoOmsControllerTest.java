package com.liyulin.demo.mall.product.test.cases.oms;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.product.test.data.ProductInfoData;
import com.liyulin.demo.rpc.product.request.api.PageProductReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductDeleteReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductInsertReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductUpdateReqBody;
import com.liyulin.demo.rpc.product.response.base.ProductInfoBaseRespBodyBody;

public class ProductInfoOmsControllerTest extends AbstractIntegrationTest {

	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testCreate() throws Exception {
		ProductInsertReqBody productInsertReqBody = new ProductInsertReqBody();
		productInsertReqBody.setName("iphone10");
		productInsertReqBody.setSellPrice(10000L);
		productInsertReqBody.setStock(200L);
		Req<ProductInsertReqBody> req = ReqUtil.buildWithHead(productInsertReqBody);
		req.setSign("test");

		Resp<BaseDto> result = super.postJson("/oms/auth/product/productInfo/create", req,
				new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testUpdate() throws Exception {
		Long productId = 1L;
		productInfoData.insertTestData(productId);

		ProductUpdateReqBody productUpdateReqBody = new ProductUpdateReqBody();
		productUpdateReqBody.setId(productId);
		productUpdateReqBody.setName("iphone10");
		productUpdateReqBody.setSellPrice(10000L);
		productUpdateReqBody.setStock(200L);
		Req<ProductUpdateReqBody> req = ReqUtil.buildWithHead(productUpdateReqBody);
		req.setSign("test");

		Resp<BaseDto> result = super.postJson("/oms/auth/product/productInfo/update", req,
				new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testLogicDelete() throws Exception {
		Long productId = 2L;
		productInfoData.insertTestData(productId);

		ProductDeleteReqBody productDeleteReqBody = new ProductDeleteReqBody();
		productDeleteReqBody.setId(productId);
		Req<ProductDeleteReqBody> req = ReqUtil.buildWithHead(productDeleteReqBody);
		req.setSign("test");

		Resp<BaseDto> result = super.postJson("/oms/auth/product/productInfo/logicDelete", req,
				new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testPageProduct() throws Exception {
		productInfoData.batchInsertTestData();
		Req<BasePageReq<PageProductReqBody>> req = ReqUtil.buildWithHead(null, 1, 10);
		req.setSign("test");

		Resp<BasePageResp<ProductInfoBaseRespBody>> result = super.postJson("/oms/auth/product/productInfo/pageProduct",
				req, new TypeReference<Resp<BasePageResp<ProductInfoBaseRespBody>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
	}

}