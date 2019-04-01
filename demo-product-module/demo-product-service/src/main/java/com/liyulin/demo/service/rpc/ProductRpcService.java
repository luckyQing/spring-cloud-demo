package com.liyulin.demo.service.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.biz.rpc.ProductRpcBiz;
import com.liyulin.demo.common.dto.BasePageReq;
import com.liyulin.demo.common.dto.BasePageResp;
import com.liyulin.demo.product.rpc.request.base.PageProductReqBody;
import com.liyulin.demo.product.rpc.response.base.ProductInfoRespBody;

/**
 * 商品信息rpc service
 *
 * @author liyulin
 * @date 2019年3月29日下午11:52:13
 */
@Service
public class ProductRpcService {

	@Autowired
	private ProductRpcBiz productRpcBiz;

	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageResp<ProductInfoRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		return productRpcBiz.pageProduct(req);
	}

}