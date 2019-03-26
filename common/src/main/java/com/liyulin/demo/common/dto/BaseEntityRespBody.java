package com.liyulin.demo.common.dto;

import java.math.BigInteger;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@ApiModel(description = "实体对象对应的响应对象基类")
public class BaseEntityRespBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id")
	private BigInteger id;

	@ApiModelProperty(value = "创建时间 ")
	private Date addTime;

	@ApiModelProperty(value = "更新时间")
	private Date updTime;

	@ApiModelProperty(value = "删除时间")
	private Date delTime;

	@ApiModelProperty(value = "新增者")
	private BigInteger addUser;

	@ApiModelProperty(value = "更新者")
	private BigInteger updUser;

	@ApiModelProperty(value = "删除者")
	private BigInteger delUser;

	@ApiModelProperty(value = "删除状态=={'1':'正常','2':'已删除'}")
	private Integer delState;

}