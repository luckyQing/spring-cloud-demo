package com.liyulin.demo.common.util;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LogUtil {
	/** 日志最大长度 */
	private static final int LOG_MAX_LENGTH = 2000;

	public static void info(String msg) {
		log.info(truncate(msg));
	}

	public static void warn(String msg) {
		log.warn(truncate(msg));
	}

	public static void error(String msg) {
		log.error(truncate(msg));
	}

	private static String truncate(String msg) {
		return StringUtils.truncate(msg, LOG_MAX_LENGTH);
	}

}