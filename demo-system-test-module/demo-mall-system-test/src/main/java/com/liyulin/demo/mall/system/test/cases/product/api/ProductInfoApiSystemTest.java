package com.liyulin.demo.mall.system.test.cases.product.api;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractSystemTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.common.util.HttpUtil;
import com.liyulin.demo.mall.system.test.config.SystemTestConfig;
import com.liyulin.demo.rpc.product.response.api.PageProductRespBody;

public class ProductInfoApiSystemTest extends AbstractSystemTest {

	@Test
	public void testPageProduct() throws IOException {
		Resp<BasePageResp<PageProductRespBody>> result = HttpUtil.postWithRaw(
				SystemTestConfig.getProductBaseUrl() + "api/identity/product/productInfo/pageProduct",
				ReqUtil.build(null, 1, 10), new TypeReference<Resp<BasePageResp<PageProductRespBody>>>() {
				});
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}