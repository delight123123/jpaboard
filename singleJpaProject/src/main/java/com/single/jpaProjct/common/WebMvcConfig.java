package com.single.jpaProjct.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/*")
				.excludePathPatterns("/login")
				.excludePathPatterns("/register")
				.excludePathPatterns("/register/idChk")
				.excludePathPatterns("/register/useremail")
				.excludePathPatterns("/register/emailcer")
				;
	}
	
}
