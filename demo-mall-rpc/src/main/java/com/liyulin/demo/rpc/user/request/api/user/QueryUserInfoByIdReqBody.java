package com.liyulin.demo.rpc.user.request.api.user;

import javax.validation.constraints.NotNull;

import com.liyulin.demo.common.business.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ApiModel(description = "根据id查询用户信息请求参数")
public class QueryUserInfoByIdReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="用户id", required=true)
	@NotNull
	private Long userId;

}