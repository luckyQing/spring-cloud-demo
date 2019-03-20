package com.liyulin.demo.mybatis.mapper.ext.util;

import org.springframework.beans.BeanUtils;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.util.StringUtil;

public class EntityColumnExt extends EntityColumn {

	public EntityColumnExt(EntityColumn entityColumn) {
		BeanUtils.copyProperties(entityColumn, this);
	}

	/**
	 * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}+separator
	 *
	 * @param entityName
	 * @param suffix
	 * @param separator
	 * @return
	 */
	public String getColumnExtHolder(String entityName, String suffix, String separator) {
		StringBuffer sb = new StringBuffer("#{");
		if (StringUtil.isNotEmpty(entityName)) {
			sb.append(entityName);
			sb.append(".");
		} else {
			sb.append("item.");
		}
		sb.append(getProperty());
		if (StringUtil.isNotEmpty(suffix)) {
			sb.append(suffix);
		}
		// 如果 null 被当作值来传递，对于所有可能为空的列，JDBC Type 是需要的
		if (getJdbcType() != null) {
			sb.append(", jdbcType=");
			sb.append(getJdbcType().toString());
		}
		// 为了以后定制类型处理方式，你也可以指定一个特殊的类型处理器类，例如枚举
		if (getTypeHandler() != null) {
			sb.append(", typeHandler=");
			sb.append(getTypeHandler().getCanonicalName());
		}
		// 3.4.0 以前的 mybatis 无法获取父类中泛型的 javaType，所以如果使用低版本，就需要设置 useJavaType = true
		// useJavaType 默认 false,没有 javaType 限制时，对 ByPrimaryKey 方法的参数校验就放宽了，会自动转型
		if (isUseJavaType() && !getJavaType().isArray()) {// 当类型为数组时，不设置javaType#103
			sb.append(", javaType=");
			sb.append(getJavaType().getCanonicalName());
		}
		sb.append("}");
		if (StringUtil.isNotEmpty(separator)) {
			sb.append(separator);
		}
		return sb.toString();
	}

}