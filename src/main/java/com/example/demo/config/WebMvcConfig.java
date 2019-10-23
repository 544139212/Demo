package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.SessionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	/**
	 * must not be private or final
	 * @return
	 */
	@Bean
	SessionInterceptor sessionInterceptor() {
		return new SessionInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		/**
		 * 如果去掉上面@Bean注解，直接这样写，则SessionInterceptor中通过@Autowired注入的redisTemplate为null
		 */
//		registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/login", "/error").addPathPatterns("/**");
		
		registry.addInterceptor(sessionInterceptor()).excludePathPatterns("/login", "/loginMock", "/error").addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

}
