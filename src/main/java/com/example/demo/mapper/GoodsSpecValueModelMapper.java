package com.example.demo.mapper;

import com.example.demo.model.GoodsSpecValueModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsSpecValueModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSpecValueModel record);

    int insertSelective(GoodsSpecValueModel record);

    GoodsSpecValueModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsSpecValueModel record);

    int updateByPrimaryKey(GoodsSpecValueModel record);
}