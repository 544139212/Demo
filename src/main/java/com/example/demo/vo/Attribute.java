package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Attribute implements Serializable {

    private Integer id;

    @NotBlank(message = "请填写属性名")
    private String name;

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
}
