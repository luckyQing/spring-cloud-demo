package com.liyulin.demo.mybatis.mapper.ext.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

import com.liyulin.demo.mybatis.mapper.ext.provider.BatchInsertExtMapperProvider;

public interface BatchInsertExtMapper<T> {

	@InsertProvider(type = BatchInsertExtMapperProvider.class, method = "dynamicSQL")
	int batchInsert(List<T> list);

}