package com.example.demo.mapper;

import com.example.demo.model.GoodsSkuSpecModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsSkuSpecModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSkuSpecModel record);

    int insertSelective(GoodsSkuSpecModel record);

    GoodsSkuSpecModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsSkuSpecModel record);

    int updateByPrimaryKey(GoodsSkuSpecModel record);
}