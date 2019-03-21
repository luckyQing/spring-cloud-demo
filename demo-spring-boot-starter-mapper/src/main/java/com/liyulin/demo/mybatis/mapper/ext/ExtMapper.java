package com.liyulin.demo.mybatis.mapper.ext;

import com.liyulin.demo.mybatis.mapper.ext.mapper.BatchInsertExtMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.BatchInsertSelectiveExtMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.BatchUpdateExtMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.BatchUpdateSelectiveExtMapper;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.Marker;

@RegisterMapper
public interface ExtMapper<T> extends Mapper<T>, BatchInsertExtMapper<T>, BatchInsertSelectiveExtMapper<T>,
		BatchUpdateExtMapper<T>, BatchUpdateSelectiveExtMapper<T>/* InsertListMapper<T> */, Marker {
	
}