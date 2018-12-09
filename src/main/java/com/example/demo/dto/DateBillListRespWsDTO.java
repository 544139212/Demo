package com.example.demo.dto;

import java.util.List;

public class DateBillListRespWsDTO {
    private int code;
    private String msg;
    private List<DateBillWsDTO> data;

    public DateBillListRespWsDTO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public DateBillListRespWsDTO(int code, String msg, List<DateBillWsDTO> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public List<DateBillWsDTO> getData() {
        return data;
    }

    public void setData(List<DateBillWsDTO> data) {
        this.data = data;
    }
}
