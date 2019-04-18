package com.liyulin.demo.mall.product.controller.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.BaseTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.rpc.product.request.api.PageProductReqBody;
import com.liyulin.demo.rpc.product.response.api.PageProductRespBody;

public class ProductInfoApiControllerTest extends BaseTest {

	@Test
	public void testPageProduct() throws Exception {
		Req<BasePageReq<PageProductReqBody>> req = ReqUtil.of(null, 1, 10);
		req.setSign("test");

		Resp<BasePageResp<PageProductRespBody>> result = super.postJson("/api/pass/product/productInfo/pageProduct",
				req, new TypeReference<Resp<BasePageResp<PageProductRespBody>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}