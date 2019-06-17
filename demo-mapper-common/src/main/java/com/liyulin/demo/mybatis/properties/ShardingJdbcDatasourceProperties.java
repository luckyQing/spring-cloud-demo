package com.liyulin.demo.mybatis.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shardingsphere.core.yaml.config.sharding.YamlShardingRuleConfiguration;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShardingJdbcDatasourceProperties extends BaseDto {

	private static final long serialVersionUID = 1L;

	private Map<String, SingleDatasourceProperties> dataSources = new HashMap<>();
    
    private Properties props = new Properties();

    private YamlShardingRuleConfiguration shardingRule;
	
}