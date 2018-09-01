package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class User implements Serializable {

    private Integer id;

    @NotBlank(message = "请填写姓名")
    private String name;

    @NotBlank(message = "请填写手机号码")
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
