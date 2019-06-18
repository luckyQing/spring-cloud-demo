package com.liyulin.demo.mall.product.test.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractUnitTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.product.test.data.ProductInfoData;
import com.liyulin.demo.rpc.product.request.api.PageProductReqBody;
import com.liyulin.demo.rpc.product.response.api.PageProductRespBody;

public class ProductInfoApiControllerTest extends AbstractUnitTest {
	
	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testPageProduct() throws Exception {
		productInfoData.batchInsertTestData();
		
		Req<BasePageReq<PageProductReqBody>> req = ReqUtil.buildWithHead(null, 1, 10);
		req.setSign("test");

		Resp<BasePageResp<PageProductRespBody>> result = super.postJson("/api/pass/product/productInfo/pageProduct",
				req, new TypeReference<Resp<BasePageResp<PageProductRespBody>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getInfo().getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
	}

}