package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Vehicle implements Serializable {

    private Integer id;

    @NotNull(message = "请填写座位数")
    private Integer seatCount;

    @NotBlank(message = "请填写车辆图片")
    private String imageUrl;

    @NotBlank(message = "请填写车牌号")
    private String no;

    @NotBlank(message = "请填写制造型号")
    private String model;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
