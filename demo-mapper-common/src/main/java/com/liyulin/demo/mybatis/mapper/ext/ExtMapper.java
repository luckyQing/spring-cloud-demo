package com.liyulin.demo.mybatis.mapper.ext;

import com.liyulin.demo.mybatis.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.mapper.ext.mapper.InsertListSelectiveMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.LogicDeleteMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.UpdateListMapper;
import com.liyulin.demo.mybatis.mapper.ext.mapper.UpdateListSelectiveMapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.additional.update.force.UpdateByPrimaryKeySelectiveForceMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.Marker;

@RegisterMapper
public interface ExtMapper<T extends BaseEntity, BigInteger> extends Mapper<T>, IdListMapper<T, BigInteger>,
		InsertListMapper<T>, InsertListSelectiveMapper<T>, UpdateListMapper<T>, UpdateListSelectiveMapper<T>,
		UpdateByPrimaryKeySelectiveForceMapper<T>, LogicDeleteMapper<T>, Marker {

}