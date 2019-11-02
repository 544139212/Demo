package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    private Integer id;

    @NotBlank(message = "请填写标题")
    private String title;

    @NotBlank(message = "请上传图片")
    private String image;

    @NotBlank(message = "请填写通知内容")
    private String content;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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