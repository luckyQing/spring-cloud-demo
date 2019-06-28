package com.liyulin.demo.mall.user.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.mall.user.biz.api.LoginInfoApiBiz;

@Service
public class LoginInfoApiService {

	@Autowired
	private LoginInfoApiBiz loginInfoApiBiz;
	
}