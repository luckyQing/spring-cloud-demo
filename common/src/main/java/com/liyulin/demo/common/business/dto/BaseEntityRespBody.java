package com.liyulin.demo.common.business.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyulin.demo.common.util.DateUtil;

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
@ApiModel(description = "实体对象对应的响应对象基类")
public class BaseEntityRespBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id")
	private Long id;

	@ApiModelProperty(value = "创建时间 ")
	@JSONField(format = DateUtil.FOROMAT_DATETIME)
	private Date addTime;

	@ApiModelProperty(value = "更新时间")
	@JSONField(format = DateUtil.FOROMAT_DATETIME)
	private Date updTime;

	@ApiModelProperty(value = "删除时间")
	@JSONField(format = DateUtil.FOROMAT_DATETIME)
	private Date delTime;

	@ApiModelProperty(value = "新增者")
	private Long addUser;

	@ApiModelProperty(value = "更新者")
	private Long updUser;

	@ApiModelProperty(value = "删除者")
	private Long delUser;

	@ApiModelProperty(value = "删除状态=={'1':'正常','2':'已删除'}")
	private Integer delState;

}