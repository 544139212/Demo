package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Plan implements Serializable {

    private Integer id;

    private Integer userId;

    @NotNull(message = "请填写始发站")
    private Integer stationStart;

    @NotNull(message = "请填写终到站")
    private Integer stationEnd;

    @NotBlank(message = "请填写发车时间")
    private String time;

    private String remark;

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

    public Integer getStationStart() {
        return stationStart;
    }

    public void setStationStart(Integer stationStart) {
        this.stationStart = stationStart;
    }

    public Integer getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(Integer stationEnd) {
        this.stationEnd = stationEnd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
