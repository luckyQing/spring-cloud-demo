package com.liyulin.demo.mybatis.mapper.ext.mapper;

import java.util.List;

import org.apache.ibatis.annotations.UpdateProvider;

import com.liyulin.demo.mybatis.mapper.ext.provider.BatchUpdateExtMapperProvider;

/**
 * 批量更新（null值也更新）
 *
 * @author liyulin
 * @date 2019年3月24日下午8:39:42
 */
public interface UpdateListMapper<T> {

	/**
	 * 批量更新（null值也更新
	 * 
	 * @param list
	 * @return
	 */
	@UpdateProvider(type = BatchUpdateExtMapperProvider.class, method = "dynamicSQL")
	int updateListByPrimaryKey(List<T> list);

}