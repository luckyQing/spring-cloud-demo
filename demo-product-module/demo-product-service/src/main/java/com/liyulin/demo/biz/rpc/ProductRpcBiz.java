package com.liyulin.demo.biz.rpc;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.mybatis.biz.BaseBiz;
import com.liyulin.demo.mybatis.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.mapper.enums.DelStateEnum;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.domain.mapper.ProductInfoBaseMapper;
import com.liyulin.demo.rpc.product.request.base.PageProductReqBody;
import com.liyulin.demo.rpc.product.response.base.ProductInfoRespBody;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 商品信息rpc biz
 *
 * @author liyulin
 * @date 2019年3月31日下午4:51:08
 */
@Repository
public class ProductRpcBiz extends BaseBiz<ProductInfoEntity> {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;

	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageResp<ProductInfoRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		Example example = new Example(ProductInfoEntity.class);
		Criteria criteria = example.createCriteria();
		PageProductReqBody reqBody = req.getQuery();
		if (!Objects.isNull(reqBody) && StringUtils.isNotBlank(reqBody.getName())) {
			criteria.andLike(ProductInfoEntity.Columns.NAME.getProperty(), reqBody.getName() + "%");
		}
		criteria.andEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.DELETED.getDelState());
		example.orderBy(BaseEntity.Columns.ID.getProperty()).desc();

		return productInfoBaseMapper.pageRespByExample(example, req.getPageNum(), req.getPageSize());
	}

}