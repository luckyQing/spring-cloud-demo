package com.liyulin.demo.common.properties;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * api配置
 * 
 * @author liyulin
 * @date 2019年6月19日 下午10:24:02
 */
@Getter
@Setter
public class ApiProperties extends BaseDto {

	private static final long serialVersionUID = 1L;
	/** hibernate validator开关 （默认false） */
	private boolean validator = false;
	/** api版本 */
	private String apiVersion;
	/** 重复提交校验开关 */
	private boolean repeatSubmitCheck = false;
	
}