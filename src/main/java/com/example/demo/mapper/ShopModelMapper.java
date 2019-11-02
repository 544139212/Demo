package com.example.demo.mapper;

import com.example.demo.model.ShopModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShopModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopModel record);

    int insertSelective(ShopModel record);

    ShopModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopModel record);

    int updateByPrimaryKey(ShopModel record);

    List<ShopModel> search(ShopModel record);
}