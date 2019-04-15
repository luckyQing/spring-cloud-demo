package com.liyulin.demo.common.business.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.liyulin.demo.common.web.validation.constraints.MaxPast;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(description = "请求头部")
public class ReqHead extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "全局唯一交易流水号", required = true, example = "eb9f81e7cee1c000")
	@NotBlank
	private String transactionId;

	@ApiModelProperty(value = "请求令牌")
	private String token;

	@ApiModelProperty(value = "接口版本号", required = true, example = "1.0.0")
	@NotBlank
	private String apiVersion;

	@ApiModelProperty(value = "请求时间戳", required = true, example = "1554551377629")
	@NotNull
	@MaxPast(value = 120000, message = "请求时间错误")
	private long timestamp;

}