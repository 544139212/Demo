package com.example.demo.mapper;

import com.example.demo.model.CategoryAttributeModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryAttributeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryAttributeModel record);

    int insertSelective(CategoryAttributeModel record);

    CategoryAttributeModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryAttributeModel record);

    int updateByPrimaryKey(CategoryAttributeModel record);
}