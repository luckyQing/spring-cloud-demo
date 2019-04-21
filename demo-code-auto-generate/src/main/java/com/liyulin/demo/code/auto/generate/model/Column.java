package com.liyulin.demo.code.auto.generate.model;

import com.liyulin.demo.code.auto.generate.config.ConfigFactory;
import com.liyulin.demo.code.auto.generate.utils.JavaTypeResolverUtils;

public class Column {
	private String columnName;
	private String columnComment;
	private JavaTypeResolverUtils.JdbcTypeInformation type;
	private int length;
	private boolean nullable;
	private int scale;
	private boolean primaryKey = false;
	private String jdbcType;
	private boolean autoIncrement;
	
	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public boolean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public JavaTypeResolverUtils.JdbcTypeInformation getType() {
		return type;
	}

	public void setType(JavaTypeResolverUtils.JdbcTypeInformation type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	
}