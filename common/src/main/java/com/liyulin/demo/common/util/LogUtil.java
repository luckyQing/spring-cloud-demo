package com.liyulin.demo.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.message.ParameterizedMessage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志打印封装，超过指定长度，截取掉
 *
 * @author liyulin
 * @date 2019年3月23日上午11:37:26
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogUtil {

	/** 日志最大长度 */
	private static final int LOG_MAX_LENGTH = 2048;

	public static void trace(String msg) {
		log.trace(truncate(msg));
	}

	public static void trace(String format, Object... args) {
		log.trace(truncate(format, args));
	}

	public static void debug(String msg) {
		log.debug(truncate(msg));
	}

	public static void debug(String format, Object... args) {
		log.debug(truncate(format, args));
	}

	public static void info(String msg) {
		log.info(truncate(msg));
	}

	public static void info(String format, Object... args) {
		log.info(truncate(format, args));
	}

	public static void warn(String msg) {
		log.warn(truncate(msg));
	}

	public static void warn(String format, Object... args) {
		log.warn(truncate(format, args));
	}

	public void warn(String msg, Throwable t) {
		log.warn(msg, t);
	}

	public static void error(String msg) {
		log.error(truncate(msg));
	}

	public static void error(String format, Object... args) {
		log.error(truncate(format, args));
	}

	public void error(String msg, Throwable t) {
		log.error(msg, t);
	}

	private static String truncate(String format, Object... args) {
		String msg = ParameterizedMessage.format(format, args);
		return StringUtils.truncate(msg, LOG_MAX_LENGTH);
	}

	private static String truncate(String msg) {
		return StringUtils.truncate(msg, LOG_MAX_LENGTH);
	}

}