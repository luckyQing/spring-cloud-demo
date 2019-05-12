package com.liyulin.demo.mall.product.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.mall.product.biz.api.ProductInfoApiBiz;
import com.liyulin.demo.rpc.product.request.api.PageProductReqBody;
import com.liyulin.demo.rpc.product.response.api.PageProductRespBody;

/**
 * 商品信息 api service
 *
 * @author liyulin
 * @date 2019年3月29日下午11:52:13
 */
@Service
public class ProductInfoApiService {
	
	@Autowired
	private ProductInfoApiBiz productOmsBiz;

	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	@Transactional
	public BasePageResp<PageProductRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		return productOmsBiz.pageProduct(req);
	}
	
}