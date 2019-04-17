package com.liyulin.demo.common.test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnNotWebApplication
public class FiegnMockAgent {

}