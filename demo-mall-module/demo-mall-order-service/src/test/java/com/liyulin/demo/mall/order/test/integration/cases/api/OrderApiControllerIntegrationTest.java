package com.liyulin.demo.mall.order.test.integration.cases.api;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.order.service.api.OrderApiService;
import com.liyulin.demo.rpc.order.request.api.CreateOrderProductInfoReqBody;
import com.liyulin.demo.rpc.order.request.api.CreateOrderReqBody;
import com.liyulin.demo.rpc.order.response.api.CreateOrderRespBody;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

@Rollback
@Transactional
public class OrderApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testCreate() throws Exception {
		// 1、构建请求
		// build args
		List<CreateOrderProductInfoReqBody> buyProducts = new ArrayList<>();
		for (long i = 1; i <= 3; i++) {
			CreateOrderProductInfoReqBody createOrderProductInfoReqBody = new CreateOrderProductInfoReqBody();
			createOrderProductInfoReqBody.setProductId(i);
			createOrderProductInfoReqBody.setBuyCount((int) i);

			buyProducts.add(createOrderProductInfoReqBody);
		}
		
		CreateOrderReqBody reqBody = new CreateOrderReqBody();
		reqBody.setProducts(buyProducts);
				
		// 2、mock 行为
		ProductInfoRpc productInfoRpc = Mockito.mock(ProductInfoRpc.class);
		OrderApiService orderApiService = applicationContext.getBean(OrderApiService.class);
		setMockAttribute(orderApiService, productInfoRpc);
		mockStubbing(productInfoRpc, buyProducts);
		
		Resp<CreateOrderRespBody> resp = postWithNoHeaders("/api/identity/order/order/create", reqBody, new TypeReference<Resp<CreateOrderRespBody>>() {
		});

		// 3、断言结果
		Assertions.assertThat(resp).isNotNull();
		Assertions.assertThat(resp.getHead()).isNotNull();
		Assertions.assertThat(resp.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}
	
	private void mockStubbing(ProductInfoRpc productInfoRpc, List<CreateOrderProductInfoReqBody> buyProducts) {
		// 2.1qryProductByIds
		// response
		List<QryProductByIdRespBody> productInfos = new ArrayList<>();
		for(CreateOrderProductInfoReqBody buyProduct:buyProducts){
			Long productId = buyProduct.getProductId();
			
			// response
			QryProductByIdRespBody qryProductByIdRespBody = new QryProductByIdRespBody();
			qryProductByIdRespBody.setId(productId);
			qryProductByIdRespBody.setName("手机"+productId);
			qryProductByIdRespBody.setSellPrice(productId*10000);
			qryProductByIdRespBody.setStock(productId*10000);
			productInfos.add(qryProductByIdRespBody);
		}
		QryProductByIdsRespBody qryProductByIdsRespBody = new QryProductByIdsRespBody(productInfos);
		// stubbing
		Mockito.when(productInfoRpc.qryProductByIds(Mockito.any())).thenReturn(RespUtil.success(qryProductByIdsRespBody));
		
		// 2.2updateStock
		// stubbing
		Mockito.when(productInfoRpc.updateStock(Mockito.any())).thenReturn(RespUtil.success());
	}

}