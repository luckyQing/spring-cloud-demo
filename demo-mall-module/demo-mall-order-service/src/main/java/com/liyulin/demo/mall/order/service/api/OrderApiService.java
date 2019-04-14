package com.liyulin.demo.mall.order.service.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.rpc.order.request.api.CreateOrderProductInfoReqBody;
import com.liyulin.demo.rpc.order.request.api.CreateOrderReqBody;
import com.liyulin.demo.rpc.order.response.api.CreateOrderRespBody;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdsReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

/**
 * 订单api service
 *
 * @author liyulin
 * @date 2019年4月8日上午12:49:20
 */
@Service
public class OrderApiService {

	@Autowired
	private ProductInfoRpc productInfoRpc;

	/**
	 * 创建订单
	 * 
	 * @param req
	 * @return
	 */
	public Resp<CreateOrderRespBody> create(Req<CreateOrderReqBody> req) {
		List<CreateOrderProductInfoReqBody> products = req.getBody().getProducts();
		// 1、查询商品信息
		List<Long> productIds = products.stream().map(product -> {
			return product.getProductId();
		}).collect(Collectors.toList());

		QryProductByIdsReqBody qryProductByIdsReqBody = QryProductByIdsReqBody.builder().ids(productIds).build();
		Resp<QryProductByIdsRespBody> qryProductByIdsResp = productInfoRpc.qryProductByIds(ReqUtil.of(qryProductByIdsReqBody));
		if (!RespUtil.isSuccess(qryProductByIdsResp)) {
			return RespUtil.error(qryProductByIdsResp);
		}
		
		// 2、创建订单信息
		
		
		// 3、扣减库存
		
		return null;
	}

}