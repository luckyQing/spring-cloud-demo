package com.liyulin.demo.mybatis.mapper.ext.mapper;

import java.util.List;

import org.apache.ibatis.annotations.UpdateProvider;

import com.liyulin.demo.mybatis.mapper.ext.provider.BatchUpdateExtMapperProvider;

public interface UpdateListSelectiveMapper<T> {

	@UpdateProvider(type = BatchUpdateExtMapperProvider.class, method = "dynamicSQL")
	int updateListByPrimaryKeySelective(List<T> list);

}