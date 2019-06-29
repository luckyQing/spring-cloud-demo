package com.liyulin.demo.rpc.user.response.api.login;

import com.liyulin.demo.common.business.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(description = "获取rsa key响应信息")
public class GetRsaKeyRespBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("访问token")
	private String token;

	@ApiModelProperty("rsa modulus")
	private String rsaModulus;

	@ApiModelProperty("rsa公钥")
	private String rsaPubKey;

}