package com.vanda.vRbac.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages="com.vanda.vRbac") //<context:component-scan>
@PropertySource(encoding="UTF-8",value="classpath:application.properties")
public class ContextConfig {

}
