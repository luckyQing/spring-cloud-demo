package com.liyulin.demo.common.util;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.liyulin.demo.common.business.dto.RespHead;
import com.liyulin.demo.common.business.exception.BaseException;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

import lombok.experimental.UtilityClass;

/**
 * 异常工具类
 *
 * @author liyulin
 * @date 2019年4月9日下午6:04:40
 */
@UtilityClass
public class ExceptionUtil {
	/** 换行符 */
	private String lineSeparator = System.getProperty("line.separator");

	/**
	 * 异常信息序列化
	 * 
	 * @param t
	 * @return
	 */
	public String toString(Throwable t) {
		StackTraceElement[] stackTraceElements = t.getStackTrace();
		return t.getClass().getTypeName() + lineSeparator + StringUtils.join(stackTraceElements, lineSeparator);
	}

	public RespHead parse(Throwable e) {
		if (e instanceof BindException) {
			// 参数校验
			BindException exs = (BindException) e;
			List<ObjectError> errorList = exs.getAllErrors();
			if (CollectionUtil.isNotEmpty(errorList)) {
				ObjectError objectError = errorList.get(0);
				return new RespHead(ReturnCodeEnum.VALIDATE_FAIL.getCode(), objectError.getDefaultMessage());
			}
		}
		if (e instanceof ConstraintViolationException) {
			// 参数校验
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
			Set<ConstraintViolation<?>> constraintViolationSet = constraintViolationException.getConstraintViolations();
			if (CollectionUtil.isNotEmpty(constraintViolationSet)) {
				for (ConstraintViolation<?> eConstraintViolation : constraintViolationSet) {
					return new RespHead(ReturnCodeEnum.VALIDATE_FAIL.getCode(), eConstraintViolation.getMessage());
				}
			}
			return new RespHead(ReturnCodeEnum.VALIDATE_FAIL.getCode(), e.getMessage());
		}
		if (e instanceof MethodArgumentNotValidException) {
			// 参数校验
			List<ObjectError> objectErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
			StringBuilder sb = new StringBuilder();
			if (CollectionUtil.isNotEmpty(objectErrors)) {
				for (int i = 0, size = objectErrors.size(); i < size; i++) {
					if (size > 1) {
						sb.append((i + 1) + ".");
					}
					String validateField = ArrayUtil.isNotEmpty(objectErrors.get(i).getCodes()) ? objectErrors.get(i).getCodes()[0]
							: StringUtils.EMPTY;
					sb.append(validateField + "-" + objectErrors.get(i).getDefaultMessage());
					if (i < size - 1) {
						sb.append("; ");
					}
				}
			}
			return new RespHead(ReturnCodeEnum.VALIDATE_FAIL.getCode(), sb.toString());
		}
		if (e instanceof IllegalArgumentException) {
			return new RespHead(ReturnCodeEnum.VALIDATE_FAIL.getCode(), e.getMessage());
		}
		if (e instanceof HttpRequestMethodNotSupportedException) {
			return new RespHead(ReturnCodeEnum.REQUEST_METHOD_NOT_SUPPORTED.getCode(), e.getMessage());
		}
		if (e instanceof HttpMediaTypeNotSupportedException) {
			return new RespHead(ReturnCodeEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), e.getMessage());
		}
		if (e instanceof MaxUploadSizeExceededException) {
			return new RespHead(ReturnCodeEnum.UPLOAD_FILE_SIZE_EXCEEDED.getCode(), e.getMessage());
		}
		if (e instanceof NoHandlerFoundException) {
			return new RespHead(ReturnCodeEnum.REQUEST_URL_ERROR.getCode(), e.getMessage());
		}
		if (e instanceof BaseException) {
			BaseException ex = (BaseException) e;
			return new RespHead(ex.getReturnCode());
		}
		if (e != null) {
			return new RespHead(ReturnCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
		}

		return new RespHead(ReturnCodeEnum.SERVER_ERROR);
	}

}