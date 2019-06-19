package com.liyulin.demo.common.business.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.properties.SmartProperties;

@Configuration
@EnableConfigurationProperties(SmartProperties.class)
public class SmartConfigure {

}