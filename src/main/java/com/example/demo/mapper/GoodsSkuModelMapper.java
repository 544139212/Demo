package com.example.demo.mapper;

import com.example.demo.model.GoodsSkuModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsSkuModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSkuModel record);

    int insertSelective(GoodsSkuModel record);

    GoodsSkuModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsSkuModel record);

    int updateByPrimaryKey(GoodsSkuModel record);
}