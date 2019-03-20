package com.liyulin.demo.mybatis.mapper.ext.provider;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class BatchUpdateExtMapperProvider extends MapperTemplate {

	public BatchUpdateExtMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	/**
	 * 通过主键更新全部字段
	 *
	 * @param ms
	 */
	public String batchUpdateByPrimaryKey(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(SqlHelper.updateSetColumns(entityClass, null, false, false));
		sql.append(SqlHelper.wherePKColumns(entityClass, true));
		return sql.toString();
	}

	/**
	 * 通过主键更新不为null的字段
	 *
	 * @param ms
	 * @return
	 */
	public String batchUpdateByPrimaryKeySelective(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(SqlHelper.updateSetColumns(entityClass, null, true, isNotEmpty()));
		sql.append(SqlHelper.wherePKColumns(entityClass, true));
		return sql.toString();
	}

}