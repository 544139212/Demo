package com.example.demo.mapper;

import com.example.demo.model.CategoryGoodsSpecModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryGoodsSpecModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryGoodsSpecModel record);

    int insertSelective(CategoryGoodsSpecModel record);

    CategoryGoodsSpecModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryGoodsSpecModel record);

    int updateByPrimaryKey(CategoryGoodsSpecModel record);
}