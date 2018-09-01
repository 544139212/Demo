package com.example.demo.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.context.Session;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.UserModelMapper;
import com.example.demo.model.UserModel;
import com.example.demo.util.EncryptUtils;
import com.example.demo.util.WeixinUtils;
import com.example.demo.vo.Result;

@RestController
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	UserModelMapper userModelMapper;

	/**
	 * 微信session过期，本地未过期，token可用。需要用到开放平台再提示过期，wx.login刷新session_key
	 * 本地session过期， 没有凭证，必须wx.login:
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/login")
	public Result<String> login(HttpServletRequest request, HttpServletResponse response) {
		Result<String> result = new Result<>();
		try {
			String code = request.getParameter("code");
			if (StringUtils.isEmpty(code)) {
				result.setCode(ResponseStatusEnum.ERROR.getCode());
				result.setMsg(ResponseStatusEnum.ERROR.getMsg());
				return result;
			}
			Map<String, Object> map = WeixinUtils.getOpenId(code);
			if (map.containsKey("errcode") && 40029 == (Integer)map.get("errcode")) {
				result.setCode(ResponseStatusEnum.ERROR.getCode());
				result.setMsg(ResponseStatusEnum.ERROR.getMsg());
				return result;
			} 
			
			String openid = (String)map.get("openid");
			String sessionKey = (String)map.get("session_key");
			
			UserModel userModel = userModelMapper.getUserByOpenid(openid);
			if (userModel == null) {
				userModel = new UserModel();
				userModel.setOpenid(openid);
				userModelMapper.insertSelective(userModel);
			}
			
			Session session = new Session();
//			session.setUnionid();
			session.setOpenid(openid);
			session.setSessionKey(sessionKey);
			session.setUserId(userModel.getId());

            String token = EncryptUtils.md5(UUID.randomUUID().toString());
            redisTemplate.opsForValue().set(token, session);
            redisTemplate.expire(token, 30*60, TimeUnit.SECONDS);
            
            result.setCode(ResponseStatusEnum.SUCCESS.getCode());
			result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
			result.setData(token);
			return result;
            
		} catch (Exception e) {
			logger.error("login error", e);
		}
		return null;
	}

}
