package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {
    private Integer id;

    @NotBlank(message = "请填写标题")
    private String title;

    @NotBlank(message = "请填写链接")
    private String link;

    @NotBlank(message = "请上传图片")
    private String image;

    @NotNull(message = "请选择开始时间")
    private Date dateFr;

    @NotNull(message = "请选择结束时间")
    private Date dateTo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getDateFr() {
        return dateFr;
    }

    public void setDateFr(Date dateFr) {
        this.dateFr = dateFr;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}