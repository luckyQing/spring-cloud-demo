package com.liyulin.demo.service.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.biz.rpc.ProductInfoRpcBiz;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;

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

}