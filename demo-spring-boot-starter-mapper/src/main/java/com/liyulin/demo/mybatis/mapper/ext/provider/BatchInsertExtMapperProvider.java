package com.liyulin.demo.mybatis.mapper.ext.provider;

import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;

import com.liyulin.demo.mybatis.mapper.ext.util.EntityColumnExt;
import com.liyulin.demo.mybatis.mapper.ext.util.EntityExtHelper;
import com.liyulin.demo.mybatis.mapper.ext.util.SqlExtHelper;

import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SelectKeyHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class BatchInsertExtMapperProvider extends MapperTemplate {

	public BatchInsertExtMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public String batchInsert(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		// 获取全部列
		Set<EntityColumnExt> columnList = EntityExtHelper.getColumnExts(entityClass);

		StringBuilder sql = new StringBuilder(256);
		processKey(sql, entityClass, ms, columnList);
		sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
		sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
		sql.append("VALUES");
		sql.append("<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">");
		sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		for (EntityColumnExt column : columnList) {
			if (!column.isInsertable()) {
				continue;
			}
			// 优先使用传入的属性值,当原属性property!=null时，用原属性
			// 自增的情况下,如果默认有值,就会备份到property_cache中,所以这里需要先判断备份的值是否存在
			if (column.isIdentity()) {
				sql.append(SqlExtHelper.getIfCacheNotNullExt(column, column.getColumnExtHolder(null, "_cache", ",")));
			} else {
				// 其他情况值仍然存在原property中
				sql.append(
						SqlExtHelper.getIfNotNullExt(column, column.getColumnExtHolder(null, null, ","), isNotEmpty()));
			}
			// 当属性为null时，如果存在主键策略，会自动获取值，如果不存在，则使用null
			if (column.isIdentity()) {
				sql.append(SqlExtHelper.getIfCacheIsNullExt(column, column.getColumnExtHolder(null, null, null) + ","));
			} else {
				// 当null的时候，如果不指定jdbcType，oracle可能会报异常，指定VARCHAR不影响其他
				sql.append(
						SqlExtHelper.getIfIsNullExt(column, column.getColumnExtHolder(null, null, ","), isNotEmpty()));
			}
		}
		sql.append("</trim>");
		sql.append("</foreach>");
		return sql.toString();
	}

	public String batchInsertSelective(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder(128);
		// 获取全部列
		Set<EntityColumnExt> columnList = EntityExtHelper.getColumnExts(entityClass);
		processKey(sql, entityClass, ms, columnList);

		sql.append("<foreach item=\"item\" collection=\"list\" separator=\";\">");
		sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
		sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		for (EntityColumnExt column : columnList) {
			if (!column.isInsertable()) {
				continue;
			}
			if (column.isIdentity()) {
				sql.append(column.getColumn() + ",");
			} else {
				sql.append(SqlExtHelper.getIfNotNullExt(column, column.getColumn() + ",", isNotEmpty()));
			}
		}
		sql.append("</trim>");
		sql.append("<trim prefix=\"VALUES(\" suffix=\")\" suffixOverrides=\",\">");
		for (EntityColumnExt column : columnList) {
			if (!column.isInsertable()) {
				continue;
			}
			// 优先使用传入的属性值,当原属性property!=null时，用原属性
			// 自增的情况下,如果默认有值,就会备份到property_cache中,所以这里需要先判断备份的值是否存在
			if (column.isIdentity()) {
				sql.append(SqlExtHelper.getIfCacheNotNullExt(column, column.getColumnExtHolder(null, "_cache", ",")));
			} else {
				// 其他情况值仍然存在原property中
				sql.append(
						SqlExtHelper.getIfNotNullExt(column, column.getColumnExtHolder(null, null, ","), isNotEmpty()));
			}
			// 当属性为null时，如果存在主键策略，会自动获取值，如果不存在，则使用null
			// 序列的情况
			if (column.isIdentity()) {
				sql.append(SqlExtHelper.getIfCacheIsNullExt(column, column.getColumnExtHolder(null, null, null) + ","));
			}
		}
		sql.append("</trim>");
		sql.append("</foreach>");
		return sql.toString();
	}

	private void processKey(StringBuilder sql, Class<?> entityClass, MappedStatement ms,
			Set<EntityColumnExt> columnList) {
		// Identity列只能有一个
		Boolean hasIdentityKey = false;
		// 先处理cache或bind节点
		for (EntityColumn column : columnList) {
			if (column.isIdentity()) {
				// 这种情况下,如果原先的字段有值,需要先缓存起来,否则就一定会使用自动增长
				// 这是一个bind节点
				sql.append(SqlHelper.getBindCache(column));
				// 如果是Identity列，就需要插入selectKey
				// 如果已经存在Identity列，抛出异常
				if (hasIdentityKey) {
					// jdbc类型只需要添加一次
					if (column.getGenerator() != null && "JDBC".equals(column.getGenerator())) {
						continue;
					}
					throw new MapperException(
							ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
				}
				// 插入selectKey
				SelectKeyHelper.newSelectKeyMappedStatement(ms, column, entityClass, isBEFORE(), getIDENTITY(column));
				hasIdentityKey = true;
			} else if (column.getGenIdClass() != null) {
				sql.append("<bind name=\"").append(column.getColumn())
						.append("GenIdBind\" value=\"@tk.mybatis.mapper.genid.GenIdUtil@genId(");
				sql.append("_parameter").append(", '").append(column.getProperty()).append("'");
				sql.append(", @").append(column.getGenIdClass().getCanonicalName()).append("@class");
				sql.append(", '").append(tableName(entityClass)).append("'");
				sql.append(", '").append(column.getColumn()).append("')");
				sql.append("\"/>");
			}
		}
	}

}