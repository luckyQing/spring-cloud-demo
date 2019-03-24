package com.liyulin.demo.mybatis.mapper.ext.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.liyulin.demo.common.dto.BaseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class BaseEntity extends BaseDto {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "f_id")
	private BigInteger id;

	/** 创建时间 */
	@Column(name = "f_sys_add_time")
	private Date addTime;

	/** 更新时间 */
	@Column(name = "f_sys_upd_time")
	private Date updTime;

	/** 删除时间 */
	@Column(name = "f_sys_del_time")
	private Date delTime;

	/** 新增者 */
	@Column(name = "f_sys_add_user")
	private BigInteger addUser;

	/** 更新者 */
	@Column(name = "f_sys_upd_user")
	private BigInteger updUser;

	/** 删除者 */
	@Column(name = "f_sys_del_user")
	private BigInteger delUser;

	/** 记录状态=={'1':'正常','2':'已删除'} */
	@Column(name = "f_sys_del_state")
	private Integer delState;

}