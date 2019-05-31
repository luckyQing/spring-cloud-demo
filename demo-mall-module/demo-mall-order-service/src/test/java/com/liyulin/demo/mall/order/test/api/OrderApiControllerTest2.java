package com.liyulin.demo.mall.order.test.api;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractSpringBootTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.order.controller.api.OrderApiController;
import com.liyulin.demo.rpc.order.request.api.CreateOrderProductInfoReqBody;
import com.liyulin.demo.rpc.order.request.api.CreateOrderReqBody;
import com.liyulin.demo.rpc.order.response.api.CreateOrderRespBody;
import com.liyulin.demo.rpc.product.ProductInfoRpc;

public class OrderApiControllerTest2 extends AbstractSpringBootTest {

	private MockMvc mockMvc;
	@Mock
	private ProductInfoRpc productInfoRpc;
	@InjectMocks
	private OrderApiController orderApiController;

	@Before
	public void initMock() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderApiController).build();
	}

	@Test
	public void testCreate() throws Exception {
		// 1、mock 行为

		// 2、构建请求
		// build args
		CreateOrderProductInfoReqBody createOrderProductInfoReqBody = new CreateOrderProductInfoReqBody();
		createOrderProductInfoReqBody.setProductId(4L);
		createOrderProductInfoReqBody.setBuyCount(2);

		CreateOrderReqBody reqBody = new CreateOrderReqBody();
		reqBody.setProducts(Arrays.asList(createOrderProductInfoReqBody));
		Req<CreateOrderReqBody> req = ReqUtil.buildWithHead(reqBody);
		req.setSign("test");

		RequestBuilder request = MockMvcRequestBuilders.post("/api/auth/order/order/create", req)
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON_UTF8);
		
		// 3、断言结果
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(result -> {
			String data = result.getResponse().getContentAsString();
			Resp<CreateOrderRespBody> resp = JSON.parseObject(data, new TypeReference<Resp<CreateOrderRespBody>>() {
			});

			Assertions.assertThat(resp).isNotNull();
			Assertions.assertThat(resp.getHead()).isNotNull();
			Assertions.assertThat(resp.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getInfo().getCode());
		});
	}

}