package com.example.demo.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.constant.Constant;
import com.example.demo.context.Context;
import com.example.demo.context.Session;
import com.example.demo.controller.LoginController;
import com.example.demo.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionInterceptor implements HandlerInterceptor {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		logger.debug("url:{}", request.getRequestURI());

        String token = request.getHeader(Constant.TOKEN);
        logger.debug("token:{}", token);
        
        if (StringUtils.isEmpty(token)) {
        	renderJson(request, response);
        	return false;
        }
        
        if (!redisTemplate.hasKey(token)) {
        	renderJson(request, response);
        	return false;
        }
        
        Session session = (Session)redisTemplate.opsForValue().get(token);
        if (session == null) {
        	renderJson(request, response);
        	return false;
        }
        
        // 解析用户信息
        Context.set(session);
        
        // session续约
        Long remainingTime = redisTemplate.getExpire(token, TimeUnit.SECONDS);
        if (remainingTime < 60) {
        	redisTemplate.expire(token, 30*60, TimeUnit.SECONDS);
        }
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	private void renderJson(HttpServletRequest request, HttpServletResponse response) {
		try {
			Result<Void> result = new Result<>();
        	result.setCode(-1);
        	result.setMsg("请重新登录");
        	
			String contentType = "application/json; charset=UTF-8";
	        response.setContentType(contentType);
	        response.getWriter().write(mapper.writeValueAsString(result));
	        response.getWriter().flush();
		} catch (IOException e) {
			logger.error("renderJson error", e);
		}
	}

}
