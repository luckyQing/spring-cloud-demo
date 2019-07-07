package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SmartSpringCloudApplication(componentBasePackages = "com.liyulin", feignClientBasePackages = "com.liyulin.demo.rpc")
@YamlScan(locationPatterns = "classpath*:/application-*.yml")
public @interface DefaultSmartSpringCloudApplication {

}