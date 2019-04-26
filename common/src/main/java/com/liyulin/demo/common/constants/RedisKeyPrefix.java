package com.liyulin.demo.common.constants;

import lombok.experimental.UtilityClass;

/**
 * redis key前缀
 *
 * @author liyulin
 * @date 2019年4月6日上午2:00:34
 */
@UtilityClass
public class RedisKeyPrefix {
	
	/** redis key分隔符 */
	public static final String REDIS_KEY_SEPARATOR = SymbolConstants.COLON;
	/** redis key前缀 */
	public static final String REDIS_KEY_PREPIX = "demo" + REDIS_KEY_SEPARATOR;
	/** 数据 */
	public static final String DATA = REDIS_KEY_PREPIX + "data" + REDIS_KEY_SEPARATOR;
	/** 缓存 */
	public static final String CACHE = REDIS_KEY_PREPIX + "cache" + REDIS_KEY_SEPARATOR;
	/** 锁 */
	public static final String LOCK = REDIS_KEY_PREPIX + "lock" + REDIS_KEY_SEPARATOR;

}