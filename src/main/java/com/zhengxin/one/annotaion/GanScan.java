package com.zhengxin.one.annotaion;

import com.zhengxin.one.config.GanBeanDefinitionRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GanBeanDefinitionRegister.class)
public @interface GanScan {
      String value() default "";
}
