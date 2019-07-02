package com.liyulin.demo.mall.product.test.integration.cases.oms;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.product.test.data.ProductInfoData;
import com.liyulin.demo.rpc.product.request.oms.ProductDeleteReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductInsertReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductUpdateReqBody;
import com.liyulin.demo.rpc.product.response.base.ProductInfoBaseRespBody;

public class ProductInfoOmsControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testCreate() throws Exception {
		ProductInsertReqBody productInsertReqBody = new ProductInsertReqBody();
		productInsertReqBody.setName("iphone10");
		productInsertReqBody.setSellPrice(10000L);
		productInsertReqBody.setStock(200L);

		Resp<BaseDto> result = super.postWithNoHeaders("/oms/auth/product/productInfo/create", productInsertReqBody,
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

		Resp<BaseDto> result = super.postWithNoHeaders("/oms/auth/product/productInfo/update", productUpdateReqBody,
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

		Resp<BaseDto> result = super.postWithNoHeaders("/oms/auth/product/productInfo/logicDelete",
				productDeleteReqBody, new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testPageProduct() throws Exception {
		productInfoData.batchInsertTestData();

		Resp<BasePageResp<ProductInfoBaseRespBody>> result = super.postWithNoHeaders(
				"/oms/auth/product/productInfo/pageProduct", ReqUtil.build(null, 1, 10),
				new TypeReference<Resp<BasePageResp<ProductInfoBaseRespBody>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
	}

}