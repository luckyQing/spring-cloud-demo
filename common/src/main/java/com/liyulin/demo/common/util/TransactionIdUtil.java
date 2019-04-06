package com.liyulin.demo.common.util;

import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局交易流水号工具类
 * 
 * <p>
 * <b>结构如下：</b>41(timestamp)+16(ip)+18(pid)+12(seq)
 * <p>
 * 改造雪花算法，将ip作为机器标志，进程号作为数据中心
 *
 * @author liyulin
 * @date 2019年4月6日下午5:53:51
 */
@Slf4j
public final class TransactionIdUtil {
	/**
	 * 起始的时间戳
	 */
	private final static long START_STMP = 1480166465631L;

	/**
	 * 每一部分占用的位数
	 */
	private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
	private final static long IP_BIT = 16; // 机器标识占用的位数
	private final static long PID_BIT = 18;// 数据中心占用的位数

	/**
	 * 每一部分的最大值
	 */
	private final static long MAX_PID_NUM = -1L ^ (-1L << PID_BIT);
	private final static long MAX_IP_NUM = -1L ^ (-1L << IP_BIT);
	private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

	/**
	 * 每一部分向左的位移
	 */
	private final static long IP_LEFT = SEQUENCE_BIT;
	private final static long PID_LEFT = SEQUENCE_BIT + IP_BIT;
	private final static long TIMESTMP_LEFT = PID_LEFT + PID_BIT;

	private long ip; // 数据中心
	private long pid; // 机器标识

	private long sequence = 0L; // 序列号
	private volatile long lastStmp = -1L;// 上一次时间戳

	private static TransactionIdUtil idWorker = new TransactionIdUtil();

	public static TransactionIdUtil getInstance() {
		return idWorker;
	}

	private TransactionIdUtil() {
		String ipStr = null;
		try {
			ipStr = WebUtil.getLocalIP().replaceAll("\\.", "");
		} catch (UnknownHostException e) {
			log.error(e.getMessage(), e);
		}
		ip = Long.valueOf(ipStr);
		if (ip > MAX_IP_NUM || ip < 0) {
			throw new IllegalArgumentException("IPId can't be greater than MAX_IP_NUM or less than 0");
		}
		pid = Long.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]) & MAX_PID_NUM;
		if (pid > MAX_PID_NUM || pid < 0) {
			throw new IllegalArgumentException("PIDId can't be greater than MAX_PID_NUM or less than 0");
		}
	}

	/**
	 * 产生下一个ID
	 *
	 * @return
	 */
	public synchronized String nextId() {
		long currStmp = getNewstmp();
		if (currStmp < lastStmp) {
			throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
		}

		if (currStmp == lastStmp) {
			// 相同毫秒内，序列号自增
			sequence = (sequence + 1) & MAX_SEQUENCE;
			// 同一毫秒的序列数已经达到最大
			if (sequence == 0L) {
				currStmp = getNextMill();
			}
		} else {
			// 不同毫秒内，序列号置为0
			sequence = 0L;
		}

		lastStmp = currStmp;
		long id = (currStmp - START_STMP) << TIMESTMP_LEFT // 时间戳部分
				| ip << IP_LEFT // 数据中心部分
				| pid << PID_LEFT // 机器标识部分
				| sequence; // 序列号部分

		return Long.toHexString(id);
	}

	private long getNextMill() {
		long mill = getNewstmp();
		while (mill <= lastStmp) {
			mill = getNewstmp();
		}
		return mill;
	}

	private long getNewstmp() {
		return System.currentTimeMillis();
	}

}