package com.liyulin.demo.common.business.exception.dto;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 返回code dto
 *
 * @author liyulin
 * @date 2019年5月1日下午12:10:11
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCodeDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	/** 状态码 */
	private String code;

	/** 提示信息 */
	private String message;

}