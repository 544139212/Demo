package com.example.demo.vo;

import com.example.demo.enums.AppointTypeEnum;
import com.example.demo.enums.PlanTypeEnum;
import com.example.demo.validator.EcDate;
import com.example.demo.validator.EcEnum;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Plan implements Serializable {

    private Integer id;

    private Integer userId;

    @NotNull(message = "请选择行程类型")
    @EcEnum(clazz = PlanTypeEnum.class, message = "行程类型选择无效")
    private Byte planType;

    @NotNull(message = "请填写行程区间")
    private Integer stationStart;

    @NotNull(message = "请填写行程区间")
    private Integer stationEnd;

    @NotNull(message = "请选择出发时间")
    @EcEnum(clazz = AppointTypeEnum.class, message = "出发时间选择无效")
    private Byte appointType;

    @EcDate(message = "日期格式错误")
    private String date;

    @EcDate(pattern = "HH:mm", message = "时间格式错误")
    private String time;

//    @NotNull(message = "请填写出行人数|剩余空位")
//    @Min(value = 1L, message = "出行人数|剩余空位至少为1")
    private Integer num;

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

    public Byte getPlanType() {
        return planType;
    }

    public void setPlanType(Byte planType) {
        this.planType = planType;
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

    public Byte getAppointType() {
        return appointType;
    }

    public void setAppointType(Byte appointType) {
        this.appointType = appointType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
