package com.liyulin.demo.mybatis.mapper.ext.util;

import java.util.Set;
import java.util.stream.Collectors;

import tk.mybatis.mapper.mapperhelper.EntityHelper;

public class EntityExtHelper extends EntityHelper {

	/**
	 * 获取全部列
	 *
	 * @param entityClass
	 * @return
	 */
	public static Set<EntityColumnExt> getColumnExts(Class<?> entityClass) {
		return getEntityTable(entityClass).getEntityClassColumns().stream().map(entity -> {
			return new EntityColumnExt(entity);
		}).collect(Collectors.toSet());
	}

}