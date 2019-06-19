package com.liyulin.demo.common.test.cases.business.autoconfigure;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import com.liyulin.demo.common.business.autoconfigure.AsyncAutoConfigure;

import junit.framework.TestCase;

public class AsyncAutoConfigureTest extends TestCase {
	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(AsyncAutoConfigure.class));

	@Test
	public void testEnableTrue() {
		contextRunner.withPropertyValues("smart.async=true")
				.run((context) -> Assertions.assertThat(context).hasSingleBean(AsyncAutoConfigure.class));
	}

	@Test
	public void testEnableFalse() {
		ConfigurableEnvironment environment = new StandardEnvironment();
		TestPropertyValues.of("smart.async=true").applyTo(environment);
		ConfigurableApplicationContext context = new SpringApplicationBuilder(AsyncAutoConfigure.class).environment(environment).web(WebApplicationType.NONE)
				.run();
		Assertions.assertThat(context.getBean(AsyncAutoConfigure.class)).isNotNull();
	}
	
	@Test
	public void testEnableFalse1() {
		WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(AsyncAutoConfigure.class));
		contextRunner.run((context) -> Assertions.assertThat(context).hasSingleBean(AsyncAutoConfigure.class));
	}

}