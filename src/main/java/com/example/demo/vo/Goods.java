package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Goods {
    private Integer id;

    @NotBlank(message = "请填写商品名称")
    private String name;

    @NotNull(message = "请选择商品类型")
    private Byte goodsType;

    private Integer storeId;

    @NotNull(message = "请选择商品分类")
    private Integer categoryId;

    @NotNull(message = "请选择商品品牌")
    private Integer brandId;

    @NotNull(message = "请选择商品单位")
    private Integer unitId;

    @NotBlank(message = "请上传商品图片")
    private String imageUrl;

//    @NotBlank(message = "请上传商品视频")
    private String videoUrl;

    private Byte saleStatus;

    private Byte auditStatus;

    @NotNull(message = "请选择运费模板")
    private Integer deliveryTemplateId;

    @NotBlank(message = "请填写商品详情")
    private String content;

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
        this.name = name == null ? null : name.trim();
    }

    public Byte getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public Byte getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Byte saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getDeliveryTemplateId() {
        return deliveryTemplateId;
    }

    public void setDeliveryTemplateId(Integer deliveryTemplateId) {
        this.deliveryTemplateId = deliveryTemplateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}