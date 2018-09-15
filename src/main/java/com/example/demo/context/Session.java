package com.example.demo.context;

import java.io.Serializable;

public class Session implements Serializable {

	private String sessionKey;
	private Integer userId;

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
