package com.liyulin.demo.mall.product.test.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liyulin.demo.mall.product.entity.base.ProductInfoEntity;
import com.liyulin.demo.mall.product.mapper.base.ProductInfoBaseMapper;
import com.liyulin.demo.mybatis.common.mapper.enums.DelStateEnum;

@Component
public class ProductInfoData {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;

	/**
	 * 插入指定id的数据
	 * 
	 * @param id
	 */
	public void insertTestData(Long id) {
		ProductInfoEntity entity = new ProductInfoEntity();
		entity.setId(id);
		entity.setName("iphone");
		entity.setSellPrice(1000L);
		entity.setStock(2000L);
		entity.setAddTime(new Date());
		entity.setDelState(DelStateEnum.NORMAL.getDelState());
		productInfoBaseMapper.insertSelective(entity);
	}

	/**
	 * 批量插入数据
	 */
	public void batchInsertTestData() {
		List<Long> list = new ArrayList<>();
		for (long id = 100; id < 121; id++) {
			list.add(id);
		}
		
		batchInsertTestData(list);
	}

	/**
	 * 批量插入数据
	 */
	public void batchInsertTestData(List<Long> ids) {
		List<ProductInfoEntity> list = new ArrayList<>();
		for (Long id : ids) {
			ProductInfoEntity entity = new ProductInfoEntity();
			entity.setId(id);
			entity.setName("iphone");
			entity.setSellPrice(1000L);
			entity.setStock(2000L);
			entity.setAddTime(new Date());
			entity.setDelState(DelStateEnum.NORMAL.getDelState());

			list.add(entity);
		}
		productInfoBaseMapper.insertListSelective(list);
	}

}