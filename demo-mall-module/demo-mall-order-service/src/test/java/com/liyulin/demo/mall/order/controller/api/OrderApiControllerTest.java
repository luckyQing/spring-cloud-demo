package com.liyulin.demo.mall.order.controller.api;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.BaseTest;
import com.liyulin.demo.common.business.test.FiegnMockAspect;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.common.util.MockUtil;
import com.liyulin.demo.rpc.order.request.api.CreateOrderProductInfoReqBody;
import com.liyulin.demo.rpc.order.request.api.CreateOrderReqBody;
import com.liyulin.demo.rpc.order.response.api.CreateOrderRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

public class OrderApiControllerTest extends BaseTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testCreate() throws Exception {
		// mock
		Resp<QryProductByIdsRespBody> qryProductByIdsResp = MockUtil.mock(Resp.class, QryProductByIdsRespBody.class);
		List<QryProductByIdRespBody> list = qryProductByIdsResp.getBody().getProductInfos();
		list.get(0).setId(4L);
		FiegnMockAspect.push(qryProductByIdsResp);
		
		Resp<BaseDto> updateStockResp = MockUtil.mock(Resp.class, BaseDto.class);
		FiegnMockAspect.push(updateStockResp);
		
		// build args
		CreateOrderProductInfoReqBody createOrderProductInfoReqBody = new CreateOrderProductInfoReqBody();
		createOrderProductInfoReqBody.setProductId(4L);
		createOrderProductInfoReqBody.setBuyCount(2);
		
		CreateOrderReqBody reqBody = new CreateOrderReqBody();
		reqBody.setProducts(Arrays.asList(createOrderProductInfoReqBody));
		Req<CreateOrderReqBody> req = ReqUtil.of(reqBody);
		req.setSign("test");

		// send req
		Resp<CreateOrderRespBody> result = super.postJson("/api/pass/order/order/create",
				req, new TypeReference<Resp<CreateOrderRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}
	
}