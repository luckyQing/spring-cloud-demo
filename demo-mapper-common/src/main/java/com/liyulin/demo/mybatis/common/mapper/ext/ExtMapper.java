package com.liyulin.demo.mybatis.common.mapper.ext;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liyulin.demo.common.business.dto.BaseEntityRespBody;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.util.ClassUtil;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.mybatis.common.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.mapper.InsertListSelectiveMapper;
import com.liyulin.demo.mybatis.common.mapper.ext.mapper.LogicDeleteMapper;
import com.liyulin.demo.mybatis.common.mapper.ext.mapper.UpdateListByExamplesMapper;
import com.liyulin.demo.mybatis.common.mapper.ext.mapper.UpdateListByExamplesSelectiveMapper;
import com.liyulin.demo.mybatis.common.mapper.ext.mapper.UpdateListByPrimaryKeyMapper;
import com.liyulin.demo.mybatis.common.mapper.ext.mapper.UpdateListByPrimaryKeySelectiveMapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.additional.update.force.UpdateByPrimaryKeySelectiveForceMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.entity.Example;

/**
 * 通用mapper超类
 *
 * @param <T> entity
 * @param <R> entity对应的resp对象
 * @param <PK> 表主键类型
 * @author liyulin
 * @date 2019年3月31日下午4:16:10
 */
@RegisterMapper
public interface ExtMapper<T extends BaseEntity, R extends BaseEntityRespBody, PK> extends Mapper<T>,
		IdListMapper<T, PK>, InsertListMapper<T>, InsertListSelectiveMapper<T>, UpdateListByPrimaryKeyMapper<T>,
		UpdateListByPrimaryKeySelectiveMapper<T>, UpdateByPrimaryKeySelectiveForceMapper<T>,
		UpdateListByExamplesMapper<T>, UpdateListByExamplesSelectiveMapper<T>, LogicDeleteMapper<T>, Marker {

	/**
	 * 根据example条件分页查询，返回entity对象
	 * 
	 * @param example
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	default BasePageResp<T> pageEntityByExample(Example example, int pageNum, int pageSize) {
		Page<T> page = PageHelper.startPage(pageNum, pageSize, true);
		List<T> datas = selectByExample(example);

		return new BasePageResp<>(datas, pageNum, pageSize, page.getTotal());
	}

	/**
	 * 根据example条件分页查询，返回resp对象
	 * 
	 * @param example
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	default BasePageResp<R> pageRespByExample(Example example, int pageNum, int pageSize) {
		Page<T> page = PageHelper.startPage(pageNum, pageSize, true);
		List<T> entitydatas = selectByExample(example);
		if (CollectionUtil.isEmpty(entitydatas)) {
			return new BasePageResp<>(null, pageNum, pageSize, page.getTotal());
		}

		Class<R> clazz = ClassUtil.getActualTypeArgumentFromSuperGenericInterface(getClass(), 1);
		List<R> respDatas = new ArrayList<>(entitydatas.size());
		for (T item : entitydatas) {
			R r = BeanUtils.instantiateClass(clazz);
			BeanUtils.copyProperties(item, r);
			respDatas.add(r);
		}

		return new BasePageResp<>(respDatas, pageNum, pageSize, page.getTotal());
	}

	/**
	 * 根据entity条件分页查询，返回entity对象
	 * 
	 * @param entity
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	default BasePageResp<T> pageEntityByEntity(T entity, int pageNum, int pageSize) {
		Page<T> page = PageHelper.startPage(pageNum, pageSize, true);
		List<T> datas = select(entity);

		return new BasePageResp<>(datas, pageNum, pageSize, page.getTotal());
	}

	/**
	 * 根据example条件分页查询，返回resp对象
	 * 
	 * @param example
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	default BasePageResp<R> pageRespByEntity(T entity, int pageNum, int pageSize) {
		Page<T> page = PageHelper.startPage(pageNum, pageSize, true);
		List<T> entitydatas = select(entity);
		if (CollectionUtil.isEmpty(entitydatas)) {
			return new BasePageResp<>(null, pageNum, pageSize, page.getTotal());
		}

		Class<R> clazz = ClassUtil.getActualTypeArgumentFromSuperGenericInterface(getClass(), 1);
		List<R> respDatas = new ArrayList<>(entitydatas.size());
		for (T item : entitydatas) {
			R r = BeanUtils.instantiateClass(clazz);
			BeanUtils.copyProperties(item, r);
			respDatas.add(r);
		}

		return new BasePageResp<>(respDatas, pageNum, pageSize, page.getTotal());
	}

}