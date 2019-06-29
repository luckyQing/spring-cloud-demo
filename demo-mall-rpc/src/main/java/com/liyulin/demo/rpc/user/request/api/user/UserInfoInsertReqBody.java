package com.liyulin.demo.rpc.user.request.api.user;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@ApiModel(description = "添加用户请求参数")
public class UserInfoInsertReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "手机号", required = true)
	@Size(max = 11)
	@NotBlank
	private String mobile;

	@ApiModelProperty(value = "昵称")
	@Size(max = 45)
	private String nickname;

	@ApiModelProperty(value = "真实姓名")
	@Size(max = 45)
	private String realname;

	@ApiModelProperty(value = "性别=={\"1\":\"男\",\"2\":\"女\",\"3\":\"未知\"}")
	@Size(max = 1)
	private Byte sex;

	@ApiModelProperty(value = "出生年月")
	private Date birthday;

	@ApiModelProperty(value = "头像")
	@Size(max = 255)
	private String profileImage;

	@ApiModelProperty(value = "所在平台=={\"1\":\"app\",\"2\":\"web后台\",\"3\":\"微信\"}", required = true)
	@Size(max = 1)
	@NotNull
	private Byte channel;

}