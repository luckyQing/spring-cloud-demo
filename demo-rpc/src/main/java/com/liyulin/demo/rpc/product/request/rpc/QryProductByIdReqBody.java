package com.liyulin.demo.rpc.product.request.rpc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.liyulin.demo.common.business.dto.BaseDto;

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
@ApiModel(description="根据id查询商品信息请求参数")
public class QryProductByIdReqBody extends BaseDto{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "商品id", required = true)
	@NotNull
	@Min(1)
	private Long id;

}