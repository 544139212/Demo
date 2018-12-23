package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    private Integer id;

    @NotBlank(message = "请填写分类名称")
    private String name;

    private String imageUrl;

    private Integer parentId;

    private List<Category> children;

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
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
