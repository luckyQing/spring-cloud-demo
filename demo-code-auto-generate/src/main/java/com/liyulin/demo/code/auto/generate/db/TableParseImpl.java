package com.liyulin.demo.code.auto.generate.db;

import com.liyulin.demo.code.auto.generate.config.ConfigFactory;
import com.liyulin.demo.code.auto.generate.config.DbConfig;
import com.liyulin.demo.code.auto.generate.model.Column;
import com.liyulin.demo.code.auto.generate.model.Table;
import com.liyulin.demo.code.auto.generate.utils.JavaTypeResolverUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableParseImpl extends BaseDaoSupport implements TableParse {

	/**
	 * 获取当前数据库下的所有表名称
	 */
	public List<String> getAllTables() {
		if (ConfigFactory.config.getDb().getUrl().contains("mysql")) {
			String tableSchema = ConfigFactory.config.getDb().getCatalog();
			return this.list(
					"select table_name from information_schema.tables where table_schema='"
							+ tableSchema + "' and table_type='base table'",
					new ResultHandler<String>() {
						@Override
						public String doInvoke(ResultSet rs) throws SQLException {
							return rs.getString(1);
						}
					});
		} else {
			return new ArrayList<String>();
		}

	}

	/**
	 * 获取当前数据库下的所有表名称
	 * @return
	 * @throws Exception
	 */
	public List getAllTableName() throws Exception {
		List tables = new ArrayList();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SHOW TABLES ");
		while (rs.next()) {
			String tableName = rs.getString(1);
			tables.add(tableName);
		}
		rs.close();
		stmt.close();
		conn.close();
		return tables;
	}

	@SuppressWarnings("resource")
	public Table getTable(String tableName) {
		Table table = new Table();
		table.setName(tableName);
		Connection con = null;
		ResultSet rs = null;
		List<Column> columns = null;
		List<Column> primaryKeyColumn = new ArrayList<>();
		//List<Column> primaryKeysList = new ArrayList<Column>();
		try {
			con = this.getConnection();
			columns = new ArrayList<Column>();
			List<String> primaryKeys = new ArrayList<String>();
			DatabaseMetaData databaseMetaData = this.getConnection().getMetaData();
			DbConfig db = ConfigFactory.config.getDb();
			
			String tableComment = getCommentByTableName(tableName);
			table.setTableComment(tableComment);
			
			//System.out.println(databaseMetaData.getDatabaseProductName());
			rs = databaseMetaData.getPrimaryKeys(db.getCatalog(), db.getSchema(), tableName);
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				primaryKeys.add(columnName);
			}
			rs = databaseMetaData.getColumns(db.getCatalog(), db.getSchema(), tableName, null);
			/*int count = rs.getMetaData().getColumnCount();
			for(int i=1;i<=count;i++){
				System.out.println(rs.getMetaData().getColumnName(i));
			}*/
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				int type = rs.getInt("DATA_TYPE");
				String comment = rs.getString("REMARKS");
				int scale = rs.getInt("DECIMAL_DIGITS");
				int length = rs.getInt("COLUMN_SIZE");
				boolean nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
				String typeName = rs.getString("TYPE_NAME");
				boolean autoIncrement = "YES".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT"));

				Column cd = new Column();
				cd.setAutoIncrement(autoIncrement);
				cd.setColumnName(name);
				cd.setType(JavaTypeResolverUtils.calculateJavaType(type, typeName, scale, length));
				cd.setColumnComment(comment);
				cd.setScale(scale);
				cd.setLength(length);
				cd.setNullable(nullable);
				cd.setJdbcType(cd.getType().getJdbcTypeName());
				table.setPrimaryKeys(primaryKeyColumn);
				for (String key : primaryKeys) {
					if (key.equals(name)) {
						cd.setPrimaryKey(true);
						primaryKeyColumn.add(cd);
						break;
					}
				}
				columns.add(cd);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			this.close(con, null, rs);
		}
		table.setColumns(columns);
		return table;
	}

	/**
	 * 获得某表的建表语句
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public String getCommentByTableName(String tableName) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
		String comment = "";
		if (rs != null && rs.next()) {
			String createDDL = rs.getString(2);
			comment = parse(createDDL);
		}
		rs.close();
		stmt.close();
		conn.close();
		return comment;
	}

	/**
	 * 返回注释信息
	 * @param all
	 * @return
	 */

	public String parse(String all) {
		String comment = null;
		int index = all.indexOf("COMMENT='");
		if (index < 0) {
			return "";
		}
		comment = all.substring(index + 9);
		int last= comment.indexOf("'");
		comment = comment.substring(0, last);
		return comment;
	}
}
