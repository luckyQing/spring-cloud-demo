package com.liyulin.demo.code.auto.generate.model;


import java.util.List;

public class Table {
	/**
	 * 表名
	 */
	private String name;
	/**
	 * 表注释
	 */
	private String tableComment;

	private List<Column> columns = null;
	private List<Column> primaryKeys;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public  List<Column> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<Column> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

}
