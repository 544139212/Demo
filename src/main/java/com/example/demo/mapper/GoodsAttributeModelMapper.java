package com.example.demo.mapper;

import com.example.demo.model.GoodsAttributeModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsAttributeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsAttributeModel record);

    int insertSelective(GoodsAttributeModel record);

    GoodsAttributeModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsAttributeModel record);

    int updateByPrimaryKey(GoodsAttributeModel record);
}