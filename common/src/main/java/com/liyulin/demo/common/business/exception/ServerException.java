package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

/**
 * 服务器异常
 *
 * @author liyulin
 * @date 2019年6月29日下午2:15:05
 */
public class ServerException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public ServerException(IBaseReturnCode baseReturnCode) {
		super(baseReturnCode);
	}

}