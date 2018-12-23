package com.example.demo.mapper;

import com.example.demo.model.GoodsModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsModel record);

    int insertSelective(GoodsModel record);

    GoodsModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsModel record);

    int updateByPrimaryKeyWithBLOBs(GoodsModel record);

    int updateByPrimaryKey(GoodsModel record);

    List<GoodsModel> search(GoodsModel record);
}