package com.liyulin.demo.rpc.user.request.api.login;

import javax.validation.constraints.NotBlank;

import com.liyulin.demo.common.business.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@ApiModel(description = "缓存aes key请求参数")
public class CacheDesKeyReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "aes key", required = true)
	@NotBlank
	private String key;

}