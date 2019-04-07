package com.liyulin.demo.biz.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.mybatis.biz.BaseBiz;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.domain.mapper.ProductInfoBaseMapper;
import com.liyulin.demo.rpc.product.request.rpc.QryProductByIdReqBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;

/**
 * 商品信息rpc biz
 *
 * @author liyulin
 * @date 2019年3月31日下午4:51:08
 */
@Repository
public class ProductInfoRpcBiz extends BaseBiz<ProductInfoEntity> {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;

	/**
	 * 根据id查询商品信息
	 * 
	 * @param reqBody
	 * @return
	 */
	public QryProductByIdRespBody qryProductById(QryProductByIdReqBody reqBody) {
		ProductInfoEntity entity = productInfoBaseMapper.selectByPrimaryKey(reqBody.getId());
		if (ObjectUtil.isNull(entity)) {
			return null;
		}

		return QryProductByIdRespBody.builder()
				.id(entity.getId())
				.name(entity.getName())
				.sellPrice(entity.getSellPrice())
				.stock(entity.getStock())
				.build();
	}

}