package com.liyulin.demo.mybatis.mapper.ext.mapper;

import java.util.List;

import org.apache.ibatis.annotations.UpdateProvider;

import com.liyulin.demo.mybatis.mapper.ext.provider.BatchUpdateExtMapperProvider;

public interface BatchUpdateSelectiveExtMapper<T> {

	@UpdateProvider(type = BatchUpdateExtMapperProvider.class, method = "dynamicSQL")
	int batchUpdateByPrimaryKeySelective(List<T> list);

}