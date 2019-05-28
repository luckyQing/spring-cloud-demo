package com.liyulin.demo.mybatis.properties;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.constants.CommonConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * 多数据源配置
 * 
 * @author liyulin
 * @date 2019年5月25日 下午3:56:55
 */
@Getter
@Setter
@ConfigurationProperties(prefix = CommonConstants.SMART_PROPERTIES_PREFIX)
public class MultipleDatasourceProperties extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	/** 多数据源配置信息 */
	private Map<String, SingleDatasourceProperties> datasources = new LinkedHashMap<>();

	/**
	 * 单数据源配置属性
	 *
	 * @author liyulin
	 * @date 2019年4月24日下午2:59:08
	 */
	@Getter
	@Setter
	public static class SingleDatasourceProperties extends BaseDto {

		private static final long serialVersionUID = 1L;

		/** jdbc url */
		private String url;

		/** 数据库用户名 */
		private String username;

		/** 数据库密码 */
		private String password;

		/** mapper interface文件所在包名 */
		private String mapperInterfaceLocation;

		/** mapper xml文件所在包名 */
		private String mapperXmlLocation;

		/** entity别名的包名 */
		private String typeAliasesPackage;

		/** 事务所在的顶层包名（多个包名用英文逗号隔开） */
		private String transactionBasePackages;

		/** jdbc驱动类名 */
		private String driverClassName = "com.mysql.cj.jdbc.Driver";

	}

}