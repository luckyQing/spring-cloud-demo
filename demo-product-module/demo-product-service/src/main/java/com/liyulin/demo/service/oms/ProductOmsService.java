package com.liyulin.demo.service.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.biz.oms.ProductOmsBiz;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.product.rpc.request.oms.ProductInsertReqBody;

/**
 * 商品信息 oms service
 *
 * @author liyulin
 * @date 2019年3月29日下午11:52:13
 */
@Service
public class ProductOmsService {

	@Autowired
	private ProductOmsBiz productOmsBiz;
	
	public Resp<BaseDto> create(ProductInsertReqBody reqBody) {
		productOmsBiz.insert(reqBody);
		return RespUtil.success();
	}
	
}