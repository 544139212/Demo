package com.example.demo.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class Spec implements Serializable {
    private Integer id;

    @NotBlank(message = "请填写商品规格名称")
    private String name;

    @NotNull(message = "请选择商品分类")
    private Integer categoryId;

    List<SpecValue> specValueList;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<SpecValue> getSpecValueList() {
        return specValueList;
    }

    public void setSpecValueList(List<SpecValue> specValueList) {
        this.specValueList = specValueList;
    }
}
