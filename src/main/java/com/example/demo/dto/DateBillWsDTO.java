package com.example.demo.dto;


import java.util.List;

public class DateBillWsDTO {

    private String date;

    List<BillWsDTO> list;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BillWsDTO> getList() {
        return list;
    }

    public void setList(List<BillWsDTO> list) {
        this.list = list;
    }
}
