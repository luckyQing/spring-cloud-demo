package com.liyulin.demo.common.business.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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

	@ApiModelProperty(value = "全局唯一交易流水号（防止重复提交）", required = true, example = "eb9f81e7cee1c000")
	@NotBlank(message = "nonce不能为空")
	@Length(min = 16, max = 17, message = "nonce格式错误")
	private String nonce;

	@ApiModelProperty(value = "请求令牌")
	private String token;

	@ApiModelProperty(value = "请求时间戳（2分钟内有效）", required = true, example = "1554551377629")
	//@MaxPast(value = 120000, message = "请求时间错误")
	private long timestamp;

}