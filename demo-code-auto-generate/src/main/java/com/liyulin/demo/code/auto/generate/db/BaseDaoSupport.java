package com.liyulin.demo.code.auto.generate.db;

import com.liyulin.demo.code.auto.generate.config.ConfigFactory;
import com.liyulin.demo.code.auto.generate.config.DbConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDaoSupport {
	protected static final Logger log = LoggerFactory.getLogger(BaseDaoSupport.class);

	public static volatile Connection connection;

	public BaseDaoSupport() {

	}

	public Connection getConnection() {
		if (connection != null){
			return connection;
		}else {
			synchronized (BaseDaoSupport.class){
				if (connection != null){
					return connection;
				}
				return createConnect();
			}
		}
	}

	private Connection createConnect(){
		DbConfig dbConfigVo = ConfigFactory.config.getDb();
		try {
			String driveName = null;
			if (dbConfigVo.getUrl().contains("oracle")) {
				driveName = "oracle.jdbc.driver.OracleDriver";
			} else if (dbConfigVo.getUrl().contains("mysql")) {
				driveName = "com.mysql.cj.jdbc.Driver";
			}
			Class.forName(driveName);
			return DriverManager.getConnection(dbConfigVo.getUrl(), dbConfigVo.getUsername(), dbConfigVo.getPassword());
		} catch (SQLException | ClassNotFoundException e) {
			log.error("", e);
		}
		return null;
	}

	public <T> List<T> list(String sql, ResultHandler<T> handler) {
		Connection con = getConnection();
		if (con == null) {
			return null;
		}
		List<T> list = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<T>();
			while (rs.next()) {
				list.add(handler.doInvoke(rs));
			}
		} catch (SQLException e) {
			log.error("", e);
		} finally {
			close(con, ps, rs);
		}
		return list;
	}

	public void close(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			log.error("", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				log.error("", e);
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					log.error("", e);
				}
			}
		}
	}

	public static void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			log.error("", e);
		}
	}


}
