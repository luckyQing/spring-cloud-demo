package com.liyulin.demo.mall.user.biz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.mall.user.entity.base.UserInfoEntity;
import com.liyulin.demo.mall.user.mapper.base.UserInfoBaseMapper;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;

@Repository
public class UserInfoApiBiz extends BaseBiz<UserInfoEntity> {
	
	@Autowired
	private UserInfoBaseMapper userInfoBaseMapper;
	
}