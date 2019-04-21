package com.liyulin.demo.code.auto.generate.db;

import com.liyulin.demo.code.auto.generate.model.Table;

import java.util.List;

public interface TableParse {
	public List<String> getAllTables();
	
	public Table getTable(String tableName);
}
