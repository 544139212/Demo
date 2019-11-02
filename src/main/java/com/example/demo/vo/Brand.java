package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Brand implements Serializable {
    private Integer id;

    @NotBlank(message = "请填写名称（中文）")
    private String nameChi;

    @NotBlank(message = "请填写名称（英文）")
    private String nameEng;

    @NotBlank(message = "请上传图片")
    private String image;

    @NotBlank(message = "请填写品牌介绍（中文）")
    private String descChi;

    @NotBlank(message = "请填写品牌介绍（英文）")
    private String descEng;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameChi() {
        return nameChi;
    }

    public void setNameChi(String nameChi) {
        this.nameChi = nameChi == null ? null : nameChi.trim();
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng == null ? null : nameEng.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getDescChi() {
        return descChi;
    }

    public void setDescChi(String descChi) {
        this.descChi = descChi == null ? null : descChi.trim();
    }

    public String getDescEng() {
        return descEng;
    }

    public void setDescEng(String descEng) {
        this.descEng = descEng == null ? null : descEng.trim();
    }
}