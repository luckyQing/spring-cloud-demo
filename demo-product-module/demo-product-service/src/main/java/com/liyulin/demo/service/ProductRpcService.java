package com.liyulin.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.biz.ProductBiz;
import com.liyulin.demo.common.dto.BasePageReq;
import com.liyulin.demo.common.dto.BasePageResp;
import com.liyulin.demo.product.rpc.request.PageProductReqBody;
import com.liyulin.demo.product.rpc.response.ProductInfoRespBody;

/**
 * 商品信息service
 *
 * @author liyulin
 * @date 2019年3月29日下午11:52:13
 */
@Service
public class ProductRpcService {

	@Autowired
	private ProductBiz productBiz;
	
	public BasePageResp<ProductInfoRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		return productBiz.pageProduct(req);
	}
	
}