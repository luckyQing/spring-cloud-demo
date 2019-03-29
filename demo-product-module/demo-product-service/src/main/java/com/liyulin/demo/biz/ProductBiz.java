package com.liyulin.demo.biz;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.liyulin.demo.common.dto.BasePageReq;
import com.liyulin.demo.mybatis.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.mapper.enums.DelStateEnum;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.domain.mapper.ProductInfoBaseMapper;
import com.liyulin.demo.product.base.rpc.request.PageProductReqBody;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class ProductBiz {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;

	public PageInfo<ProductInfoEntity> pageProduct(BasePageReq<PageProductReqBody> req) {
		Example example = new Example(ProductInfoEntity.class);
		Criteria criteria = example.createCriteria();
		PageProductReqBody reqBody = req.getQuery();
		if (!Objects.isNull(reqBody) && StringUtils.isNotBlank(reqBody.getName())) {
			criteria.andLike(ProductInfoEntity.Columns.NAME.getProperty(), reqBody.getName() + "%");
		}
		criteria.andEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.DELETED.getDelState());
		example.orderBy(BaseEntity.Columns.ID.getProperty()).desc();

		return productInfoBaseMapper.pageByExample(example, req.getPageNum(), req.getPageSize());
	}

}