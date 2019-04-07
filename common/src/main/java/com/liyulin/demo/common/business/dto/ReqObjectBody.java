package com.liyulin.demo.common.business.dto;

import javax.validation.Valid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "单个请求参数")
public class ReqObjectBody<T> extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参数")
	@Valid
	private T object;

}