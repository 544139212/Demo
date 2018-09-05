package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Shop implements Serializable {

    private Integer id;

    private Integer userId;

    @NotBlank(message = "请填写店铺名称")
    private String name;

    @NotBlank(message = "请填写店铺地址")
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
