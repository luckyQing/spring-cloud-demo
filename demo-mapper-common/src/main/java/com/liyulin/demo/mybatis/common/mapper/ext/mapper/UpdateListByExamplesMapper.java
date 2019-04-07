package com.liyulin.demo.mybatis.common.mapper.ext.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import com.liyulin.demo.mybatis.common.mapper.ext.provider.BatchUpdateMapperProvider;

import tk.mybatis.mapper.entity.Example;

/**
 * 批量更新（null值也被更新）
 *
 * @author liyulin
 * @date 2019年4月7日下午4:42:14
 */
public interface UpdateListByExamplesMapper<T> {

	/**
	 * 根据对应的{@link Example}条件更新对应实体`record`包含的全部属性，null值会被更新
	 * 
	 * @param list
	 * @return
	 */
	@UpdateProvider(type = BatchUpdateMapperProvider.class, method = "dynamicSQL")
	int updateListByExamples(@Param("records") List<T> records, @Param("examples") List<Example> examples);
	
}