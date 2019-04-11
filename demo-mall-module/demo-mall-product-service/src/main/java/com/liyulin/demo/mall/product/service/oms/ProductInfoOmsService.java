package com.liyulin.demo.mall.product.service.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.product.biz.oms.ProductInfoOmsBiz;
import com.liyulin.demo.rpc.product.request.oms.PageProductReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductDeleteReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductInsertReqBody;
import com.liyulin.demo.rpc.product.request.oms.ProductUpdateReqBody;
import com.liyulin.demo.rpc.product.response.oms.ProductInfoRespBody;

/**
 * 商品信息 oms service
 *
 * @author liyulin
 * @date 2019年3月29日下午11:52:13
 */
@Service
public class ProductInfoOmsService {

	@Autowired
	private ProductInfoOmsBiz productOmsBiz;
	
	/**
	 * 新增
	 * 
	 * @param reqBody
	 * @return
	 */
	public Resp<BaseDto> create(ProductInsertReqBody reqBody) {
		productOmsBiz.insert(reqBody);
		return RespUtil.success();
	}
	
	/**
	 * 修改
	 * 
	 * @param reqBody
	 * @return
	 */
	public Resp<BaseDto> update(ProductUpdateReqBody reqBody) {
		productOmsBiz.update(reqBody);
		return RespUtil.success();
	}
	
	/**
	 * 逻辑删除
	 * 
	 * @param reqBody
	 * @return
	 */
	public Resp<BaseDto> logicDelete(ProductDeleteReqBody reqBody) {
		productOmsBiz.logicDelete(reqBody.getId());
		return RespUtil.success();
	}
	
	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageResp<ProductInfoRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		return productOmsBiz.pageProduct(req);
	}
	
}