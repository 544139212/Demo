package com.example.demo.enums;

public enum ResponseStatusEnum {
	SUCCESS(0, "成功"),
	ERROR(-1, "错误"),
	INVALID(-2, "参数错误"),
	NOT_FOUND(-3, "未找到相关记录"),
	AUTH(-4, "鉴权未通过"),
	EXIST(-5, "记录已存在");
	
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
