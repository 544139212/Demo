package com.example.demo.mapper;

import com.example.demo.model.CategoryBrandModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryBrandModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryBrandModel record);

    int insertSelective(CategoryBrandModel record);

    CategoryBrandModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryBrandModel record);

    int updateByPrimaryKey(CategoryBrandModel record);
}