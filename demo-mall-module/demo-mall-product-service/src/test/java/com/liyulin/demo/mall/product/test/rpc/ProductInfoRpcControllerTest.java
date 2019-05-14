package com.liyulin.demo.mall.product.test.rpc;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractSpringBootTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;

public class ProductInfoRpcControllerTest extends AbstractSpringBootTest {

	@Test
	public void testQryProductById() throws Exception {
		QryProductByIdReqBody reqBody = new QryProductByIdReqBody();
		reqBody.setId(4L);
		Req<QryProductByIdReqBody> req = ReqUtil.buildWithHead(reqBody);
		req.setSign("test");

		Resp<QryProductByIdRespBody> result = super.postJson("/rpc/pass/product/productInfo/qryProductById",
				req, new TypeReference<Resp<QryProductByIdRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getInfo().getCode());
	}
	
}