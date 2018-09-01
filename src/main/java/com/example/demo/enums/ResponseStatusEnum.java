package com.example.demo.enums;

public enum ResponseStatusEnum {
	SUCCESS(0, "success"),
	ERROR(-1, "error"),
	INVALID(-2, "invalid"),
	NOT_FOUND(-3, "not found"),
	AUTH(-4, "auth"),
	EXIST(-5, "exist");
	
	private int code;
	private String msg;
	
	ResponseStatusEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
