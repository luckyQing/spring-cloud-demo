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
@ApiModel(description = "更新库存请求参数")
public class UpdateStockReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id", required = true)
	@NotNull
	@Min(1)
	private long id;

	@ApiModelProperty(value = "商品库存更新数（正数代表扣减；负数代表回冲）", required = true)
	@NotNull
	private long count;

}