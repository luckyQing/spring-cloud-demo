package com.liyulin.demo.mybatis.common.biz;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.liyulin.demo.common.util.ClassUtil;
import com.liyulin.demo.common.util.SnowFlakeIdUtil;
import com.liyulin.demo.mybatis.common.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.common.mapper.enums.DelStateEnum;

public class BaseBiz<T extends BaseEntity> {

	/**
	 * 生成id
	 * 
	 * @return
	 */
	protected long generateId() {
		return SnowFlakeIdUtil.getInstance().nextId();
	}
	
	public T create() {
		Class<T> clazz = ClassUtil.getActualTypeArgumentFromSuperGenericClass(getClass(), 0);
		T entity = BeanUtils.instantiateClass(clazz);
		entity.setId(SnowFlakeIdUtil.getInstance().nextId());
		entity.setAddTime(new Date());
		entity.setDelState(DelStateEnum.NORMAL.getDelState());
		return entity;
	}

}