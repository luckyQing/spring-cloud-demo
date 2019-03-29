package com.liyulin.demo.common.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求参数基类
 *
 * @author liyulin
 * @date 2019年3月29日下午11:18:52
 */
@Getter
@Setter
@AllArgsConstructor
public class BasePageReq<T> extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分页请求参数（不包括分页信息）")
	@Valid
	private T query;

	@ApiModelProperty(value = "第几页，默认第1页", example = "1")
	@Min(value = 1)
	private int pageNum = 1;

	@ApiModelProperty(value = "页面数据大小，默认10", example = "10")
	@Min(value = 1)
	private int pageSize = 10;

}