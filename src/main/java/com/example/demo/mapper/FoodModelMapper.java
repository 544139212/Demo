package com.example.demo.mapper;

import com.example.demo.model.FoodModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FoodModelMapper {
    int deleteByPrimaryKey(Integer objectid);

    int insert(FoodModel record);

    int insertSelective(FoodModel record);

    FoodModel selectByPrimaryKey(Integer objectid);

    int updateByPrimaryKeySelective(FoodModel record);

    int updateByPrimaryKey(FoodModel record);

    List<FoodModel> findAll();
}