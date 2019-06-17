package com.liyulin.demo.mall.product.algorithm;

import java.util.Collection;
import java.util.Date;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * 按周分片
 * 
 * @author liyulin
 * @date 2019年6月3日 下午9:29:10
 */
public class WeekPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		return "dd";
	}

}