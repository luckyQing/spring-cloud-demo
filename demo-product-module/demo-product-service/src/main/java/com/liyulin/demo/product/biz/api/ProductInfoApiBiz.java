package com.liyulin.demo.product.biz.api;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;
import com.liyulin.demo.mybatis.common.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.common.mapper.enums.DelStateEnum;
import com.liyulin.demo.product.base.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.mapper.ProductInfoBaseMapper;
import com.liyulin.demo.rpc.product.request.api.PageProductReqBody;
import com.liyulin.demo.rpc.product.response.api.PageProductRespBody;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 商品信息api biz
 *
 * @author liyulin
 * @date 2019年3月31日下午4:51:08
 */
@Repository
public class ProductInfoApiBiz extends BaseBiz<ProductInfoEntity> {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;
	
	/**
	 * 分页查询商品信息
	 * 
	 * @param req
	 * @return
	 */
	public BasePageResp<PageProductRespBody> pageProduct(BasePageReq<PageProductReqBody> req) {
		Example example = new Example(ProductInfoEntity.class);
		Criteria criteria = example.createCriteria();
		PageProductReqBody reqBody = req.getQuery();
		if (!Objects.isNull(reqBody) && StringUtils.isNotBlank(reqBody.getName())) {
			criteria.andLike(ProductInfoEntity.Columns.NAME.getProperty(), "%" + reqBody.getName() + "%");
		}
		criteria.andEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.DELETED.getDelState());
		example.orderBy(BaseEntity.Columns.ADD_TIME.getProperty()).desc();

		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<ProductInfoEntity> entitydatas = productInfoBaseMapper.selectByExample(example);
		Page<ProductInfoEntity> page = PageHelper.getLocalPage();
		
		if (CollectionUtil.isEmpty(entitydatas)) {
			return new BasePageResp<>(null, req.getPageNum(), req.getPageSize(), 0);
		}
		
		List<PageProductRespBody> pagedatas = entitydatas.stream().map(entity->{
			PageProductRespBody pagedata = PageProductRespBody.builder()
					.id(entity.getId())
					.name(entity.getName())
					.sellPrice(entity.getSellPrice())
					.stock(entity.getStock())
					.build();
			return pagedata;
		}).collect(Collectors.toList());

		return new BasePageResp<>(pagedatas, req.getPageNum(), req.getPageSize(), page.getTotal());
	}

}