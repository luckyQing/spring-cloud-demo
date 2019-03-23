package com.liyulin.demo.mybatis.mapper.ext.util;

import java.util.Set;

import tk.mybatis.mapper.LogicDeleteException;
import tk.mybatis.mapper.annotation.LogicDelete;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.util.StringUtil;

public class SqlExtHelper extends SqlHelper {
	/**
	 * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
	 *
	 * @param column
	 * @return
	 */
	public static String getIfCacheNotNullExt(EntityColumn column, String contents) {
		StringBuilder sql = new StringBuilder();
		sql.append("<if test=\"").append(column.getProperty()).append("_cache != null\">");
		sql.append(contents);
		sql.append("</if>");
		return sql.toString();
	}

	/**
	 * 如果_cache == null
	 *
	 * @param column
	 * @return
	 */
	public static String getIfCacheIsNullExt(EntityColumn column, String contents) {
		StringBuilder sql = new StringBuilder();
		sql.append("<if test=\"").append(column.getProperty()).append("_cache == null\">");
		sql.append(contents);
		sql.append("</if>");
		return sql.toString();
	}

	/**
	 * 判断自动!=null的条件结构
	 *
	 * @param column
	 * @param contents
	 * @param empty
	 * @return
	 */
	public static String getIfNotNullExt(EntityColumn column, String contents, boolean empty) {
		return getIfNotNullExt(null, column, contents, empty);
	}

	/**
	 * 判断自动==null的条件结构
	 *
	 * @param column
	 * @param contents
	 * @param empty
	 * @return
	 */
	public static String getIfIsNullExt(EntityColumn column, String contents, boolean empty) {
		return getIfIsNullExt(null, column, contents, empty);
	}

	/**
	 * 判断自动!=null的条件结构
	 *
	 * @param entityName
	 * @param column
	 * @param contents
	 * @param empty
	 * @return
	 */
	public static String getIfNotNullExt(String entityName, EntityColumn column, String contents, boolean empty) {
		StringBuilder sql = new StringBuilder();
		sql.append("<if test=\"");
		if (StringUtil.isNotEmpty(entityName)) {
			sql.append(entityName).append(".");
		} else {
			sql.append("item.");
		}
		sql.append(column.getProperty()).append(" != null");
		if (empty && column.getJavaType().equals(String.class)) {
			sql.append(" and ");
			if (StringUtil.isNotEmpty(entityName)) {
				sql.append(entityName).append(".");
			} else {
				sql.append("item.");
			}
			sql.append(column.getProperty()).append(" != '' ");
		}
		sql.append("\">");
		sql.append(contents);
		sql.append("</if>");
		return sql.toString();
	}

	/**
	 * 判断自动==null的条件结构
	 *
	 * @param entityName
	 * @param column
	 * @param contents
	 * @param empty
	 * @return
	 */
	public static String getIfIsNullExt(String entityName, EntityColumn column, String contents, boolean empty) {
		StringBuilder sql = new StringBuilder();
		sql.append("<if test=\"");
		if (StringUtil.isNotEmpty(entityName)) {
			sql.append(entityName).append(".");
		} else {
			sql.append("item.");
		}
		sql.append(column.getProperty()).append(" == null");
		if (empty && column.getJavaType().equals(String.class)) {
			sql.append(" or ");
			if (StringUtil.isNotEmpty(entityName)) {
				sql.append(entityName).append(".");
			} else {
				sql.append("item.");
			}
			sql.append(column.getProperty()).append(" == '' ");
		}
		sql.append("\">");
		sql.append(contents);
		sql.append("</if>");
		return sql.toString();
	}
	
}