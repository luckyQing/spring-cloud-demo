package com.liyulin.demo.mybatis.properties;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.constants.CommonConstant;

import lombok.Getter;
import lombok.Setter;

/**
 * 多数据源配置
 * 
 * @author liyulin
 * @date 2019年5月25日 下午3:56:55
 * @since YamlShardingDataSourceFactory
 */
@Getter
@Setter
@ConfigurationProperties(prefix = CommonConstant.SMART_PROPERTIES_PREFIX)
public class MultipleDatasourceProperties extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	/** 多数据源配置信息 */
	private Map<String, SingleDatasourceProperties> datasources = new LinkedHashMap<>();
	/** sharding jdbc配置 */
	private Map<String, ShardingJdbcDatasourceProperties> shardingDatasources = new LinkedHashMap<>();
	
}