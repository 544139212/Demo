package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Shop implements Serializable {
    private Integer id;

    private Integer userId;

    @NotBlank(message = "请上传店铺logo")
    private String logo;

    @NotBlank(message = "请填写店铺名称")
    private String name;

    private Byte openStatus;

    private Byte auditStatus;

    private Byte lockStatus;

    @NotNull(message = "请选择店铺所在省份")
    private Integer provId;

    @NotNull(message = "请选择店铺所在城市")
    private Integer areaId;

    @NotNull(message = "请选择店铺所在县区")
    private Integer cityId;

    @NotBlank(message = "请填写店铺地址")
    private String address;

    @NotBlank(message = "店铺所处地理位置经度获取失败")
    private String longitude;

    @NotBlank(message = "店铺所处地理位置纬度获取失败")
    private String latitude;

    @NotBlank(message = "请填写身份证姓名")
    private String idCardName;

    @NotBlank(message = "请填写身份证号码")
    private String idCardNo;

    @NotBlank(message = "请上传身份证正面图片")
    private String idCardFrontImageUrl;

    @NotBlank(message = "请上传身份证背面图片")
    private String idCardBackImageUrl;

    @NotBlank(message = "请填写营业执照编码")
    private String licenseNo;

    @NotBlank(message = "请上传营业执照图片")
    private String licenseImageUrl;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Byte openStatus) {
        this.openStatus = openStatus;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Byte getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Byte lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Integer getProvId() {
        return provId;
    }

    public void setProvId(Integer provId) {
        this.provId = provId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName == null ? null : idCardName.trim();
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public String getIdCardFrontImageUrl() {
        return idCardFrontImageUrl;
    }

    public void setIdCardFrontImageUrl(String idCardFrontImageUrl) {
        this.idCardFrontImageUrl = idCardFrontImageUrl == null ? null : idCardFrontImageUrl.trim();
    }

    public String getIdCardBackImageUrl() {
        return idCardBackImageUrl;
    }

    public void setIdCardBackImageUrl(String idCardBackImageUrl) {
        this.idCardBackImageUrl = idCardBackImageUrl == null ? null : idCardBackImageUrl.trim();
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo == null ? null : licenseNo.trim();
    }

    public String getLicenseImageUrl() {
        return licenseImageUrl;
    }

    public void setLicenseImageUrl(String licenseImageUrl) {
        this.licenseImageUrl = licenseImageUrl == null ? null : licenseImageUrl.trim();
    }
}