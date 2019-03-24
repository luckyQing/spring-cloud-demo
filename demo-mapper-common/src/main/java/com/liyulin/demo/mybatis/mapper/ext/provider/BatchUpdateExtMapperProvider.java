package com.liyulin.demo.mybatis.mapper.ext.provider;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * 批量更新
 *
 * @author liyulin
 * @date 2019年3月24日上午12:17:15
 */
public class BatchUpdateExtMapperProvider extends MapperTemplate {

	public BatchUpdateExtMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	/**
	 * 通过主键更新全部字段
	 *
	 * @param ms
	 */
	public String updateListByPrimaryKey(MappedStatement ms) {
		String entityName = "item";
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append("<foreach item=\"" + entityName + "\" collection=\"list\" separator=\";\">");
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(SqlHelper.updateSetColumns(entityClass, entityName, false, false));
		sql.append(SqlHelper.wherePKColumns(entityClass, entityName, true));
		sql.append("</foreach>");
		return sql.toString();
	}

	/**
	 * 通过主键更新不为null的字段
	 *
	 * @param ms
	 * @return
	 */
	public String updateListByPrimaryKeySelective(MappedStatement ms) {
		String entityName = "item";
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append("<foreach item=\"" + entityName + "\" collection=\"list\" separator=\";\">");
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(SqlHelper.updateSetColumns(entityClass, entityName, true, isNotEmpty()));
		sql.append(SqlHelper.wherePKColumns(entityClass, entityName, true));
		sql.append("</foreach>");
		return sql.toString();
	}

}