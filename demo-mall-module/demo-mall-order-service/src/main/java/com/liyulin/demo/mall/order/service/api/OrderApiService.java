package com.liyulin.demo.mall.order.service.api;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.common.util.SnowFlakeIdUtil;
import com.liyulin.demo.mall.order.biz.api.OrderBillApiBiz;
import com.liyulin.demo.mall.order.biz.api.OrderDeliveryInfoApiBiz;
import com.liyulin.demo.mall.order.entity.base.OrderBillEntity;
import com.liyulin.demo.mall.order.entity.base.OrderDeliveryInfoEntity;
import com.liyulin.demo.mall.order.exception.UpdateStockException;
import com.liyulin.demo.mybatis.common.mapper.enums.DelStateEnum;
import com.liyulin.demo.rpc.enums.order.PayStateEnum;
import com.liyulin.demo.rpc.order.request.api.CreateOrderProductInfoReqBody;
import com.liyulin.demo.rpc.order.request.api.CreateOrderReqBody;
import com.liyulin.demo.rpc.order.response.api.CreateOrderRespBody;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdsReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
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
	@Autowired
	private OrderBillApiBiz orderBillApiBiz;
	@Autowired
	private OrderDeliveryInfoApiBiz orderDeliveryInfoApiBiz;

	/**
	 * 创建订单
	 * 
	 * @param req
	 * @return
	 * @throws UpdateStockException 
	 */
	// TODO:分布式事务
	public Resp<CreateOrderRespBody> create(Req<CreateOrderReqBody> req) throws UpdateStockException {
		List<CreateOrderProductInfoReqBody> products = req.getBody().getProducts();
		// 1、查询商品信息
		List<Long> productIds = products.stream().map(CreateOrderProductInfoReqBody::getProductId).collect(Collectors.toList());

		QryProductByIdsReqBody qryProductByIdsReqBody = QryProductByIdsReqBody.builder().ids(productIds).build();
		Resp<QryProductByIdsRespBody> qryProductByIdsResp = productInfoRpc
				.qryProductByIds(ReqUtil.buildWithHead(qryProductByIdsReqBody));
		if (!RespUtil.isSuccess(qryProductByIdsResp)) {
			return RespUtil.error(qryProductByIdsResp);
		}
		List<QryProductByIdRespBody> productInfos = qryProductByIdsResp.getBody().getProductInfos();

		// 2、创建订单信息
		Long orderBillId = SnowFlakeIdUtil.getInstance().nextId();

		List<OrderDeliveryInfoEntity> entities = saveOrderDeliveryInfo(orderBillId, products, productInfos);
		OrderBillEntity orderBillEntity = saveOrderBill(orderBillId, entities);

		// 3、扣减库存
		List<UpdateStockReqBody> list = products.stream().map(item->{
			UpdateStockReqBody updateStockReqBody = new UpdateStockReqBody();
			updateStockReqBody.setId(item.getProductId());
			updateStockReqBody.setCount(item.getBuyCount());
			return updateStockReqBody;
		}).collect(Collectors.toList());
		
		Resp<BaseDto> updateStockResp = productInfoRpc.updateStock(ReqUtil.build(list));
		if(RespUtil.isSuccess(updateStockResp)) {
			CreateOrderRespBody createOrderRespBody = new CreateOrderRespBody();
			createOrderRespBody.setOrderId(orderBillId);
			createOrderRespBody.setFree(orderBillEntity.getAmount()==0);
			
			return RespUtil.success(createOrderRespBody);
		}
		
		throw new UpdateStockException();
	}

	private List<OrderDeliveryInfoEntity> saveOrderDeliveryInfo(Long orderBillId,
			List<CreateOrderProductInfoReqBody> products, List<QryProductByIdRespBody> productInfos) {
		List<OrderDeliveryInfoEntity> entities = products.stream().map(item -> {
			OrderDeliveryInfoEntity entity = new OrderDeliveryInfoEntity();
			entity.setId(SnowFlakeIdUtil.getInstance().nextId());
			entity.setOrderBillId(orderBillId);
			entity.setProductInfoId(item.getProductId());
			entity.setBuyCount(item.getBuyCount());

			QryProductByIdRespBody productInfo = getproductInfo(productInfos, item.getProductId());
			entity.setPrice(productInfo.getSellPrice());
			entity.setProductName(productInfo.getName());

			entity.setAddTime(new Date());
			entity.setDelState(DelStateEnum.NORMAL.getDelState());

			return entity;
		}).collect(Collectors.toList());

		orderDeliveryInfoApiBiz.create(entities);

		return entities;
	}

	private OrderBillEntity saveOrderBill(Long orderBillId, List<OrderDeliveryInfoEntity> entities) {
		OrderBillEntity orderBillEntity = new OrderBillEntity();
		orderBillEntity.setId(orderBillId);

		Long amount = entities.stream().mapToLong(item -> item.getBuyCount() * item.getPrice()).sum();
		orderBillEntity.setAmount(amount);
		orderBillEntity.setPayState(PayStateEnum.PENDING_PAY.getState());
		orderBillEntity.setBuyer(1L);
		orderBillEntity.setAddTime(new Date());
		orderBillEntity.setDelState(DelStateEnum.NORMAL.getDelState());

		orderBillApiBiz.create(orderBillEntity);
		
		return orderBillEntity;
	}

	private QryProductByIdRespBody getproductInfo(List<QryProductByIdRespBody> productInfos, Long productId) {
		for (QryProductByIdRespBody productInfo : productInfos) {
			if (productInfo.getId().compareTo(productId) == 0) {
				return productInfo;
			}
		}

		return null;
	}

}