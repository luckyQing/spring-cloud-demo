package com.liyulin.demo.common.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(description = "请求对象")
public class Req<T extends BaseDto> {

	@ApiModelProperty(value = "请求头部", required = true)
	@NotNull
	@Valid
	private ReqHead head;

	@ApiModelProperty(value = "请求体")
	@Valid
	private T body;

	@ApiModelProperty(value = "签名", required = true)
	@NotBlank
	private String sign;

}