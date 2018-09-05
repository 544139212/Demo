package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Location implements Serializable {

    private Integer id;

    private Integer userId;

    @NotBlank(message = "请填写经度")
    private String longitude;

    @NotBlank(message = "请填写维度")
    private String latitude;

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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
