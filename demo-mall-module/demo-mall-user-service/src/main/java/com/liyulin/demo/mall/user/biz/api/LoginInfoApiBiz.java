package com.liyulin.demo.mall.user.biz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.mall.user.entity.base.LoginInfoEntity;
import com.liyulin.demo.mall.user.mapper.base.LoginInfoBaseMapper;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;

@Repository
public class LoginInfoApiBiz extends BaseBiz<LoginInfoEntity> {
	
	@Autowired
	private LoginInfoBaseMapper loginInfoBaseMapper;
	
}