package com.liyulin.demo.mybatis.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.liyulin.demo.mybatis.properties.MultipleDatasourceProperties;

/**
 * 多数据源配置
 *
 * @author liyulin
 * @date 2019年4月25日上午10:38:05
 */
@Configuration
@EnableConfigurationProperties(MultipleDatasourceProperties.class)
@EnableTransactionManagement
@Import({MultipleDataSourceInitializationConfiguration.class })
public class MultipleDataSourceAutoConfiguration {
	
}