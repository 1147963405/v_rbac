package com.vanda.vRbac.config;


import com.vanda.vRbac.interceptor.LoginStatusInterceptor;
import com.vanda.vRbac.interceptor.PermissionInterceptor;
import com.vanda.vRbac.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc //<mvc:annotation-driver>
public class MvcConfig extends WebMvcConfigurerAdapter {

	//<mvc:defaultServletHandler>
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	//视图解释器
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//登录状态判断，会话失效跳回到登录页面
		//注意实现：我们必须要在权限校验之前判断用户会话状态。因为权限校验需要使用到会话中的用户信息
		LoginStatusInterceptor loginStatusInterceptor=new LoginStatusInterceptor();
		registry.addInterceptor(loginStatusInterceptor)
		.addPathPatterns("/**")
		//排除登录不拦截
		.excludePathPatterns("/user/login")
		//排除注销不拦截
		.excludePathPatterns("/user/undo");
		
		PermissionInterceptor pInterceptor=new PermissionInterceptor();
		registry.addInterceptor(pInterceptor)
		//拦截所有请求
		.addPathPatterns("/**")
		//排除登录不拦截
		.excludePathPatterns("/user/login")
		//排除注销不拦截
		.excludePathPatterns("/user/undo");
		
		TokenInterceptor tokenInterceptor=new TokenInterceptor();
		registry.addInterceptor(tokenInterceptor)
		//拦截所有请求
		.addPathPatterns("/**")
		//排除登录不拦截
		.excludePathPatterns("/user/login")
		//排除注销不拦截
		.excludePathPatterns("/user/undo");
		
	
	}
	
	
    
}
