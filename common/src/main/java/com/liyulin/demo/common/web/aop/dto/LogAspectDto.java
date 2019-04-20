package com.liyulin.demo.common.web.aop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 切面日志Dto
 *
 * @author liyulin
 * @date 2019年4月9日下午5:00:23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LogAspectDto extends FeignAspectDto {

	private static final long serialVersionUID = 1L;

	/** 接口url */
	private String url;

	/** http请求方式 */
	private String httpMethod;

	/** 客户IP地址 */
	private String ip;

	/** 操作系统相关信息 */
	private String os;

	/** 异常堆栈信息 */
	private String exceptionStackInfo;

}