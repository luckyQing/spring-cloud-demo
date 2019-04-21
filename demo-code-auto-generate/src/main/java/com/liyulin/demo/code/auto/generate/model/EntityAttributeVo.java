package com.liyulin.demo.code.auto.generate.model;

import com.liyulin.demo.code.auto.generate.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityAttributeVo {

	/**
	 * user
	 */
	private String attributeName;

	/**
	 * User
	 */
	private String attributeType;

	/**
	 * 属性类型包名
	 */
	private String fullName;

	/**
	 * 属性枚举名称
	 */
	private String attributeEnumName;


	private Column column;

	public String getSetMethodName() {
		return StringUtils.getSetMethodName(attributeName);
	}

	public String getGetMethodName() {
		return StringUtils.getGetMethodName(attributeName, column.getType().getJavaFullName());
	}
}
