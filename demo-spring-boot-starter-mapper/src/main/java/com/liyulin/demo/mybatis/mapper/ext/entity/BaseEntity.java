package com.liyulin.demo.mybatis.mapper.ext.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseEntity {

	/** 创建时间 */
	@Column(name = "f_sys_add_time")
	protected Date addTime;

	/** 更新时间 */
	@Column(name = "f_sys_upd_time")
	protected Date updTime;

	/** 删除时间 */
	@Column(name = "f_sys_del_time")
	protected Date delTime;

	/** 新增者 */
	@Column(name = "f_sys_add_user")
	protected BigInteger addUser;

	/** 更新者 */
	@Column(name = "f_sys_upd_user")
	protected BigInteger updUser;

	/** 删除者 */
	@Column(name = "f_sys_del_user")
	protected BigInteger delUser;

	/** 记录状态=={'1':'正常','2':'已删除'} */
	@Column(name = "f_sys_del_state")
	protected Integer delState;

}