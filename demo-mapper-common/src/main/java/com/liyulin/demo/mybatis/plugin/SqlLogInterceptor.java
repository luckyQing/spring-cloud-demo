package com.liyulin.demo.mybatis.plugin;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.liyulin.demo.common.util.DateUtil;
import com.liyulin.demo.common.util.LogUtil;

/**
 * mybatis sql日志打印
 *
 * @author liyulin
 * @date 2019年3月22日下午11:31:29
 */
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class SqlLogInterceptor implements Interceptor {

	private static final String QUOTE = "\\?";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object returnValue = null;
		long start = System.currentTimeMillis();
		try {
			returnValue = invocation.proceed();
			return returnValue;
		} finally {
			long end = System.currentTimeMillis();
			long time = (end - start);
			MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
			Object parameter = null;
			if (invocation.getArgs().length > 1) {
				parameter = invocation.getArgs()[1];
			}
			String sqlId = mappedStatement.getId();
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			Configuration configuration = mappedStatement.getConfiguration();
			showSql(configuration, boundSql, sqlId, time, returnValue);
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

	/**
	 * sql日志拼接
	 * 
	 * <p>
	 * 不能用换行。如果使用换行，在logstash中日志的顺序将会混乱
	 * 
	 * @param configuration
	 * @param boundSql
	 * @param sqlId
	 * @param time
	 * @param returnValue
	 */
	public static void showSql(Configuration configuration, BoundSql boundSql, String sqlId, long time,
			Object returnValue) {
		String separator = " ==> ";
		String sql = getSql(configuration, boundSql);
		StringBuilder str = new StringBuilder((sql.length() > 256) ? 256 : 64);
		str.append(sqlId);
		str.append("：");
		str.append(sql);
		str.append(separator);
		str.append("spend：");
		str.append(time);
		str.append("ms");
		str.append(separator);
		str.append("result===>");
		str.append(returnValue);

		LogUtil.info(str.toString());
	}

	private static String getParameterValue(Object obj) {
		String params = "";
		if (obj instanceof String) {
			params = "'" + String.valueOf(obj) + "'";
		} else if (obj instanceof Date) {
			Date date = (Date) obj;
			params = "'" + DateUtil.formatDateTime(date) + "'";
		} else if (Objects.isNull(obj)) {
			params = "null";
		} else {
			params = obj.toString();
		}

		return Matcher.quoteReplacement(params);
	}

	public static String getSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (CollectionUtils.isEmpty(parameterMappings) || Objects.isNull(parameterObject)) {
			return sql;
		}

		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
			sql = sql.replaceFirst(QUOTE, getParameterValue(parameterObject));
		} else {
			MetaObject metaObject = configuration.newMetaObject(parameterObject);
			for (ParameterMapping parameterMapping : parameterMappings) {
				String propertyName = parameterMapping.getProperty();
				if (metaObject.hasGetter(propertyName)) {
					Object obj = metaObject.getValue(propertyName);
					sql = sql.replaceFirst(QUOTE, getParameterValue(obj));
				} else if (boundSql.hasAdditionalParameter(propertyName)) {
					Object obj = boundSql.getAdditionalParameter(propertyName);
					sql = sql.replaceFirst(QUOTE, getParameterValue(obj));
				}
			}
		}
		
		return sql;
	}

}