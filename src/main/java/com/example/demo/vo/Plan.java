package com.example.demo.vo;

import com.example.demo.enums.PlanTypeEnum;
import com.example.demo.validator.EcDate;
import com.example.demo.validator.EcEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Plan implements Serializable {

    private Integer id;

    private Integer userId;

    @NotNull(message = "请选择类型")
    @EcEnum(clazz = PlanTypeEnum.class, message = "类型无效")
    private Byte type;

    @NotNull(message = "请填写区间")
    private Integer stationStart;

    @NotNull(message = "请填写区间")
    private Integer stationEnd;

    @NotBlank(message = "请填写日期")
    @EcDate(message = "日期格式错误")
    private String date;

    @NotBlank(message = "请填写时间")
    @EcDate(pattern = "HH:mm", message = "时间格式错误")
    private String time;

    @NotNull(message = "请填写人数")
    @Min(value = 1L, message = "人数至少为1")
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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
