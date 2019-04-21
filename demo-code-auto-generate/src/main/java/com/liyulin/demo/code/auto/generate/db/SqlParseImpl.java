package com.liyulin.demo.code.auto.generate.db;

import com.liyulin.demo.code.auto.generate.model.Column;
import com.liyulin.demo.code.auto.generate.model.SqlResultColumn;
import com.liyulin.demo.code.auto.generate.model.SqlResultSet;
import com.liyulin.demo.code.auto.generate.utils.JavaTypeResolverUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqlParseImpl extends BaseDaoSupport  {
	
	
	
	public SqlResultSet getSqlResultSet(String voName, String sqlStr){
		SqlResultSet sqlResultSet = new SqlResultSet();
		sqlResultSet.setName(voName);
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Column> columns = null;
		try {
			 con = this.getConnection();
			 columns = new ArrayList<Column>();
			 st =this.getConnection().prepareStatement(sqlStr);
			 rs = st.executeQuery();
			 ResultSetMetaData rsm = this.getConnection().prepareStatement(sqlStr).executeQuery().getMetaData();
			int count = rsm.getColumnCount();
			for(int i=1;i<=count;i++){
				SqlResultColumn column = new SqlResultColumn();
				column.setColumnName(rsm.getColumnLabel(i));
				int type= rsm.getColumnType(i);
				String typeName = rsm.getColumnTypeName(i);
				int scale = rsm.getScale(i);
				int length = rsm.getPrecision(i);
				JavaTypeResolverUtils.calculateJavaType(type,typeName, scale, length);
				column.setType(JavaTypeResolverUtils.calculateJavaType(type,typeName, scale, length));
				column.setScale(scale);
				column.setLength(length);
				column.setJdbcType(column.getType().getJdbcTypeName());
				columns.add(column);
			}
			sqlResultSet.setColumns(columns);
		} catch (SQLException e) {
			log.error("", e);
		}finally{
			this.close(con, st, rs);
		}
		return sqlResultSet;
	}
}
