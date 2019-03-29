package com.liyulin.demo.mybatis.mapper.ext;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import tk.mybatis.mapper.entity.Example;

@RegisterMapper
public interface ExtMapper<T extends BaseEntity, BigInteger> extends Mapper<T>, IdListMapper<T, BigInteger>,
		InsertListMapper<T>, InsertListSelectiveMapper<T>, UpdateListMapper<T>, UpdateListSelectiveMapper<T>,
		UpdateByPrimaryKeySelectiveForceMapper<T>, LogicDeleteMapper<T>, Marker {

	/**
	 * 根据example条件分页查询
	 * 
	 * @param example
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	default PageInfo<T> pageByExample(Example example, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = selectByExample(example);
		return new PageInfo<>(list);
	}

	/**
	 * 根据entity条件分页查询
	 * 
	 * @param entity
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	default PageInfo<T> pageByEntity(T entity, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = select(entity);
		return new PageInfo<>(list);
	}

}