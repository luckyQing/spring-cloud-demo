package com.liyulin.demo.mall.product.service.rpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.product.biz.rpc.ProductInfoRpcBiz;
import com.liyulin.demo.mall.product.enums.ProductReturnCodeEnum;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdsReqBody;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

/**
 * 商品信息rpc service
 *
 * @author liyulin
 * @date 2019年3月29日下午11:52:13
 */
@Service
public class ProductInfoRpcService {

	@Autowired
	private ProductInfoRpcBiz productRpcBiz;

	/**
	 * 根据id查询商品信息
	 * 
	 * @param reqBody
	 * @return
	 */
	public QryProductByIdRespBody qryProductById(QryProductByIdReqBody reqBody) {
		return productRpcBiz.qryProductById(reqBody);
	}
	
	/**
	 * 根据ids查询商品信息
	 * 
	 * @param reqBody
	 * @return
	 */
	public QryProductByIdsRespBody qryProductByIds(QryProductByIdsReqBody reqBody) {
		return productRpcBiz.qryProductByIds(reqBody);
	}

	/**
	 * 扣减库存
	 * 
	 * @param list
	 * @return
	 */
	@Transactional
	public Resp<BaseDto> updateStock(List<UpdateStockReqBody> list) {
		boolean success = productRpcBiz.updateStock(list);
		return success ? RespUtil.success() : RespUtil.error(ProductReturnCodeEnum.STOCK_NOT_ENOUGH);
	}

}