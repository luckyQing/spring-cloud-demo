package com.liyulin.demo.common.web.aop;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.dto.RespHead;
import com.liyulin.demo.common.util.ExceptionUtil;
import com.liyulin.demo.common.util.LogUtil;

/**
 * 全局异常处理
 *
 * @author liyulin
 * @date 2019年4月8日下午9:05:25
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	public Resp<BaseDto> handleException(Exception e, HttpServletRequest request) {
		LogUtil.error(e.getMessage(), e);

		RespHead head = ExceptionUtil.parse(e);
		return new Resp<>(head);
	}

}