package com.liyulin.demo.common.dto;


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
@ApiModel(description="请求头部")
public class ReqHead extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "全局唯一交易流水号", required = true, example="")
	@NotBlank
	private String transactionId;
	
	@ApiModelProperty(value = "请求时间戳", required = true)
	@NotNull
	// TODO：5分钟以内
	private long timestamp;

}