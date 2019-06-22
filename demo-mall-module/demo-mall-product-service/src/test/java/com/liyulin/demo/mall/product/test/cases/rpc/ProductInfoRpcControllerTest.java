package com.liyulin.demo.mall.product.test.cases.rpc;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqObjectBody;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.product.test.data.ProductInfoData;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdsReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

public class ProductInfoRpcControllerTest extends AbstractIntegrationTest {

	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testQryProductById() throws Exception {
		Long productId = 200L;
		productInfoData.insertTestData(productId);
		QryProductByIdReqBody reqBody = new QryProductByIdReqBody();
		reqBody.setId(productId);
		Req<QryProductByIdReqBody> req = ReqUtil.buildWithHead(reqBody);
		req.setSign("test");

		Resp<QryProductByIdRespBody> result = super.postJson("/rpc/pass/product/productInfo/qryProductById", req,
				new TypeReference<Resp<QryProductByIdRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testQryProductByIds() throws Exception {
		List<Long> ids = new ArrayList<>();
		for (long id = 200; id < 211; id++) {
			ids.add(id);
		}
		productInfoData.batchInsertTestData(ids);

		QryProductByIdsReqBody qryProductByIdsReqBody = new QryProductByIdsReqBody();
		qryProductByIdsReqBody.setIds(ids);
		Req<QryProductByIdsReqBody> req = ReqUtil.buildWithHead(qryProductByIdsReqBody);
		req.setSign("test");

		Resp<QryProductByIdsRespBody> result = super.postJson("/rpc/pass/product/productInfo/qryProductByIds", req,
				new TypeReference<Resp<QryProductByIdsRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getProductInfos()).isNotEmpty();
	}

	@Test
	public void testUpdateStock() throws Exception {
		// create test data
		List<Long> ids = new ArrayList<>();
		for (long id = 300; id < 303; id++) {
			ids.add(id);
		}
		productInfoData.batchInsertTestData(ids);

		// build req params
		List<UpdateStockReqBody> updateStockReqBody = new ArrayList<>();
		for (Long id : ids) {
			updateStockReqBody.add(new UpdateStockReqBody(id, 3));
		}

		Req<ReqObjectBody<List<UpdateStockReqBody>>> req = ReqUtil.buildWithHead(updateStockReqBody);
		req.setSign("test");

		Resp<BaseDto> result = super.postJson("/rpc/pass/product/productInfo/updateStock", req,
				new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}