package com.liyulin.demo.mybatis.common.mapper.ext.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

import com.liyulin.demo.mybatis.common.mapper.ext.provider.InsertListSelectiveMapperProvider;

/**
 * 批量插入（不为null的值）
 *
 * @author liyulin
 * @date 2019年3月24日下午8:39:14
 */
public interface InsertListSelectiveMapper<T> {

	/**
	 * 批量插入（不为null的值）
	 * 
	 * @param list
	 * @return
	 */
	@InsertProvider(type = InsertListSelectiveMapperProvider.class, method = "dynamicSQL")
	int insertListSelective(List<? extends T> list);

}