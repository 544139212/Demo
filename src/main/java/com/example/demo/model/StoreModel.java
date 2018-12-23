package com.example.demo.model;

import java.util.Date;

public class StoreModel {
    private Integer id;

    private Integer userId;

    private String logo;

    private String name;

    private Byte openStatus;

    private Byte auditStatus;

    private Byte lockStatus;

    private Integer provId;

    private Integer areaId;

    private Integer cityId;

    private String address;

    private String longitude;

    private String latitude;

    private String idCardName;

    private String idCardNo;

    private String idCardFrontImageUrl;

    private String idCardBackImageUrl;

    private String licenseNo;

    private String licenseImageUrl;

    private Byte delInd;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

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

    public Byte getDelInd() {
        return delInd;
    }

    public void setDelInd(Byte delInd) {
        this.delInd = delInd;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}