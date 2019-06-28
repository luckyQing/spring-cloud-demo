package com.liyulin.demo.mall.user.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.mall.user.biz.api.UserInfoApiBiz;

@Service
public class UserInfoApIService {

	@Autowired
	private UserInfoApiBiz userInfoApiBiz;
	
}