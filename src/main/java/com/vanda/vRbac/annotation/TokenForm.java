package com.vanda.vRbac.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenForm {
	//如果create为true,表示是一个跳转到增加页面的请求
	boolean create()  default false;
	//如果是remove为true,表示是一个增加提交的请求
	boolean remove()  default false;

}
