package com.liyulin.demo.mybatis.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import lombok.experimental.UtilityClass;

/**
 * 数据库表工具类
 * 
 * @author liyulin
 * @date 2019年6月4日 下午10:07:34
 */
@UtilityClass
public class DbTableUtil {

	/** copy表结构sql */
	private static final String COPY_TABLE_SCHEMA_SQL = "CREATE TABLE ${targetTableName} LIKE ${sourceTableName}";
	/** 判断表是否存在sql */
	private static final String EXIST_TABLE_SQL = "SELECT count(*) FROM information_schema.TABLES WHERE TABLE_NAME = #{tableName}";

	/**
	 * 复制表结构
	 * 
	 * @param sourceTableName       被复制的表名
	 * @param targetTableName       复制后的表名
	 * @param sqlSessionFactoryBean
	 * @throws Exception
	 */
	public static void copyTableSchema(String sourceTableName, String targetTableName,
			SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			Map<String, String> sqlParams = new HashMap<>();
			sqlParams.put("targetTableName", targetTableName);
			sqlParams.put("sourceTableName", sourceTableName);

			SqlMapper sqlMapper = new SqlMapper(sqlSession);
			sqlMapper.selectOne(COPY_TABLE_SCHEMA_SQL, sqlParams);
		}
	}

	/**
	 * 判断表是否已存在
	 * 
	 * @param tableName             表名
	 * @param sqlSessionFactoryBean
	 * @return
	 * @throws Exception
	 */
	public boolean existTable(String tableName, SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			Map<String, String> sqlParams = new HashMap<>();
			sqlParams.put("tableName", tableName);

			SqlMapper sqlMapper = new SqlMapper(sqlSession);
			Integer count = sqlMapper.selectOne(EXIST_TABLE_SQL, sqlParams, Integer.class);
			return count == 1;
		}
	}

}