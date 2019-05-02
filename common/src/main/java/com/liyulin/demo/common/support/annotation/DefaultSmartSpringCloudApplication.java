package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.liyulin.demo.common.constants.CommonConstants;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SmartSpringCloudApplication(componentBasePackages = CommonConstants.BASE_PACAKGE, feignClientBasePackages = CommonConstants.BASE_RPC_PACAKGE)
public @interface DefaultSmartSpringCloudApplication {

}