package com.example.demo.mapper;

import com.example.demo.model.GoodsSpecModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsSpecModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSpecModel record);

    int insertSelective(GoodsSpecModel record);

    GoodsSpecModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsSpecModel record);

    int updateByPrimaryKey(GoodsSpecModel record);

    List<GoodsSpecModel> search(GoodsSpecModel record);
}