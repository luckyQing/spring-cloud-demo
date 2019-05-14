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
import com.liyulin.demo.common.business.util.RespHeadUtil;
import com.liyulin.demo.common.constants.SymbolConstants;

import lombok.experimental.UtilityClass;

/**
 * 异常工具类
 *
 * @author liyulin
 * @date 2019年4月9日下午6:04:40
 */
@UtilityClass
public class ExceptionUtil {
	
	/** 错误分隔符 */
	private String ERROR_SEPARATOR = " | ";

	/**
	 * 异常信息序列化
	 * 
	 * @param t
	 * @return
	 */
	public String toString(Throwable t) {
		StackTraceElement[] stackTraceElements = t.getStackTrace();
		return t.getClass().getTypeName() + ERROR_SEPARATOR + StringUtils.join(stackTraceElements, ERROR_SEPARATOR);
	}

	public String getErrorMsg(Set<ConstraintViolation<?>> constraintViolationSet) {
		StringBuilder errorMsg = new StringBuilder();
		int size = constraintViolationSet.size();
		int i = 0;
		for (ConstraintViolation<?> constraintViolation : constraintViolationSet) {
			if (size > 1) {
				errorMsg.append((++i) + SymbolConstants.DOT);
			}
			if (constraintViolation.getPropertyPath() == null) {
				errorMsg.append(constraintViolation.getMessage());
			} else {
				errorMsg.append(constraintViolation.getPropertyPath().toString())
						.append(SymbolConstants.HYPHEN)
						.append(constraintViolation.getMessage());
			}
			if (size > 1 && i < size) {
				errorMsg.append("; ");
			}
		}

		return errorMsg.toString();
	}

	public String getErrorMsg(List<ObjectError> objectErrors) {
		StringBuilder errorMsg = new StringBuilder();
		for (int i = 0, size = objectErrors.size(); i < size; i++) {
			if (size > 1) {
				errorMsg.append((i + 1) + SymbolConstants.DOT);
			}
			String validateField = ArrayUtil.isNotEmpty(objectErrors.get(i).getCodes())
					? objectErrors.get(i).getCodes()[0]
					: StringUtils.EMPTY;
			errorMsg.append(validateField + SymbolConstants.HYPHEN + objectErrors.get(i).getDefaultMessage());
			if (size > 1 && i < size - 1) {
				errorMsg.append("; ");
			}
		}

		return errorMsg.toString();
	}

	/**
	 * 将{@link Throwable}解析构造{@link RespHead}
	 * 
	 * @param e
	 * @return
	 */
	public RespHead parse(Throwable e) {
		if (e instanceof BindException) {
			// 参数校验
			BindException bindException = (BindException) e;
			List<ObjectError> errorList = bindException.getAllErrors();
			if (CollectionUtil.isNotEmpty(errorList)) {
				ObjectError objectError = errorList.get(0);
				return RespHeadUtil.of(ReturnCodeEnum.VALIDATE_FAIL, objectError.getDefaultMessage());
			}
		}
		if (e instanceof ConstraintViolationException) {
			// 参数校验
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
			Set<ConstraintViolation<?>> constraintViolationSet = constraintViolationException.getConstraintViolations();
			if (CollectionUtil.isNotEmpty(constraintViolationSet)) {
				String errorMsg = getErrorMsg(constraintViolationSet);
				return RespHeadUtil.of(ReturnCodeEnum.VALIDATE_FAIL, errorMsg);
			}
			return RespHeadUtil.of(ReturnCodeEnum.VALIDATE_FAIL, e.getMessage());
		}
		if (e instanceof MethodArgumentNotValidException) {
			// 参数校验
			List<ObjectError> objectErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
			if (CollectionUtil.isNotEmpty(objectErrors)) {
				String errorMsg = getErrorMsg(objectErrors);
				return RespHeadUtil.of(ReturnCodeEnum.VALIDATE_FAIL, errorMsg);
			}
			return RespHeadUtil.of(ReturnCodeEnum.VALIDATE_FAIL, e.getMessage());
		}
		if (e instanceof IllegalArgumentException) {
			return RespHeadUtil.of(ReturnCodeEnum.VALIDATE_FAIL, e.getMessage());
		}
		if (e instanceof HttpRequestMethodNotSupportedException) {
			return RespHeadUtil.of(ReturnCodeEnum.REQUEST_METHOD_NOT_SUPPORTED, e.getMessage());
		}
		if (e instanceof HttpMediaTypeNotSupportedException) {
			return RespHeadUtil.of(ReturnCodeEnum.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
		}
		if (e instanceof MaxUploadSizeExceededException) {
			return RespHeadUtil.of(ReturnCodeEnum.UPLOAD_FILE_SIZE_EXCEEDED, e.getMessage());
		}
		if (e instanceof NoHandlerFoundException) {
			return RespHeadUtil.of(ReturnCodeEnum.REQUEST_URL_ERROR, e.getMessage());
		}
		if (e instanceof BaseException) {
			BaseException ex = (BaseException) e;
			return RespHeadUtil.of(ex.getCode(), ex.getMessage());
		}
		if (e != null) {
			return RespHeadUtil.of(ReturnCodeEnum.SERVER_ERROR, e.getMessage());
		}

		return RespHeadUtil.of(ReturnCodeEnum.SERVER_ERROR, null);
	}

}