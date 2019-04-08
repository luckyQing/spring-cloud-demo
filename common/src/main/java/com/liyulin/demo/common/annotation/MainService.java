package com.liyulin.demo.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 服务启动类注解
 *
 * @author liyulin
 * @date 2019年3月31日下午4:34:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication(scanBasePackages = "com.liyulin.demo")
@EnableFeignClients(basePackages = { "com.liyulin.demo.rpc" })
@EnableDiscoveryClient
@EnableSwagger2
@EnableTransactionManagement
@MapperScan(basePackages = { "com.liyulin.demo.*.base.mapper", "com.liyulin.demo.*.mapper" })
public @interface MainService {

}






//@Target(ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.liyulin.demo", excludeFilters = {
//		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
//		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
//@EnableFeignClients(basePackages = { "com.liyulin.demo.rpc" })
//@EnableDiscoveryClient
//@EnableSwagger2
//@EnableTransactionManagement
//@MapperScan(basePackages = { "com.liyulin.demo.*.base.mapper", "com.liyulin.demo.*.mapper" })
//public @interface MainService {
//
//}