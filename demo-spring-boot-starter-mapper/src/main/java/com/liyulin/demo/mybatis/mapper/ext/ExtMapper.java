package com.liyulin.demo.mybatis.mapper.ext;

import com.liyulin.demo.mybatis.mapper.ext.mapper.InsertListSelectiveMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.UpdateListMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.UpdateListSelectiveMapper;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.Marker;

@RegisterMapper
public interface ExtMapper<T> extends Mapper<T>, InsertListMapper<T>, InsertListSelectiveMapper<T>,
		UpdateListMapper<T>, UpdateListSelectiveMapper<T>, Marker {

}