package com.liyulin.demo.mybatis.autoconfigure;

import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 多数据源配置
 *
 * @author liyulin
 * @date 2019年4月25日上午10:38:05
 */
@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@Import({ MultipleDataSourceRegistrar.class })
public class MultipleDataSourceAutoConfiguration {

}