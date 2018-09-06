package com.example.demo.mapper;

import com.example.demo.model.GoodsModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsModel record);

    int insertSelective(GoodsModel record);

    GoodsModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsModel record);

    int updateByPrimaryKey(GoodsModel record);
}