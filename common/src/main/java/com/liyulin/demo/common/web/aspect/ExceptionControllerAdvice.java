package com.liyulin.demo.common.web.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.dto.RespHead;
import com.liyulin.demo.common.util.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 *
 * @author liyulin
 * @date 2019年4月8日下午9:05:25
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Resp<BaseDto> handleException(Exception e) {
		log.error(e.getMessage(), e);

		RespHead head = ExceptionUtil.parse(e);
		return new Resp<>(head);
	}

}