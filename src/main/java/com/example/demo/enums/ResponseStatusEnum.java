package com.example.demo.enums;

public enum ResponseStatusEnum {
	SUCCESS(0, "成功"),
	ERROR(-1, "错误"),
	INVALID(-2, "参数错误"),
	NOT_FOUND(-3, "未找到相关记录"),
	AUTH(-4, "鉴权未通过"),
	EXIST(-5, "记录已存在"),

	VEHICLE_NOT_FOUND(2003, "请完善车辆信息"),
	STATION_START_INVALID(2004, "出发地无效"),
	STATION_END_INVALID(2005, "目的地无效"),
	STATION_START_END_EQUAL_ERROR(2006, "出发地和目的地不能相同"),
	DATE_INVALID(2007, "日期不能早于今天"),
	TIME_INVALID(2008, "时间不能早于现在"),
	REN_ZHAO_CHE_NUM_INVALID(2009, "出行人数必须大于0"),
	CHE_ZHAO_REN_NUM_INVALID(2010, "剩余空位必须大于0");
	
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
