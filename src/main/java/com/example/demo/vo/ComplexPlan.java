package com.example.demo.vo;

import java.io.Serializable;
import java.util.Date;

public class ComplexPlan implements Serializable {

    private Integer id;

    private User user;

    private Location location;

    private Vehicle vehicle;

    private Station stationStart;

    private Station stationEnd;

    private String date;

    private String time;

    private Integer num;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Station getStationStart() {
        return stationStart;
    }

    public void setStationStart(Station stationStart) {
        this.stationStart = stationStart;
    }

    public Station getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(Station stationEnd) {
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
