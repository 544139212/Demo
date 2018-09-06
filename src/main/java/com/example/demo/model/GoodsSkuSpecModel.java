package com.example.demo.model;

import java.util.Date;

public class GoodsSkuSpecModel {
    private Integer id;

    private Integer goodsSkuId;

    private Integer goodsSpecId;

    private Integer goodsSpecValueId;

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

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getGoodsSpecId() {
        return goodsSpecId;
    }

    public void setGoodsSpecId(Integer goodsSpecId) {
        this.goodsSpecId = goodsSpecId;
    }

    public Integer getGoodsSpecValueId() {
        return goodsSpecValueId;
    }

    public void setGoodsSpecValueId(Integer goodsSpecValueId) {
        this.goodsSpecValueId = goodsSpecValueId;
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