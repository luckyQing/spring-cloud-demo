package com.liyulin.demo.common.properties;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * 单数据源配置属性
 *
 * @author liyulin
 * @date 2019年4月24日下午2:59:08
 */
@Getter
@Setter
public class SingleDataSourceProperties extends BaseDto {

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

	/** jdbc驱动类名 */
	private String driverClassName = "com.mysql.cj.jdbc.Driver";

}