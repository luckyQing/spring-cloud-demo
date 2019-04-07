package com.liyulin.demo.product.mapper.rpc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;

/**
 * 商品rpc mapper
 *
 * @author liyulin
 * @date 2019年4月7日下午11:40:18
 */
public interface ProductInfoRpcMapper {

	/**
	 * 扣减库存
	 * 
	 * @param list
	 * @return
	 */
	int updateStock(@Param("list") List<UpdateStockReqBody> list);

}