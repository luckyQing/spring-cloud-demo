package com.liyulin.demo.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(description = "响应对象")
public class Resp<T extends BaseDto> {
	
	@ApiModelProperty(value = "响应头部")
	private ReqHead head;

	@ApiModelProperty(value = "响应体")
	private T body;
}