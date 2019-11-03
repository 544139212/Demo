package com.example.demo.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private Integer id;

    @NotBlank(message = "请填写名称")
    private String name;

    private String image;

    @Min(value = 0, message = "请关联正确的父分类")
    private Integer parentId;

    List<CategorySpec> categorySpecList;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<CategorySpec> getCategorySpecList() {
        return categorySpecList;
    }

    public void setCategorySpecList(List<CategorySpec> categorySpecList) {
        this.categorySpecList = categorySpecList;
    }
}
