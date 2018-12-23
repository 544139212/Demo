package com.example.demo.mapper;

import com.example.demo.model.StoreModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreModel record);

    int insertSelective(StoreModel record);

    StoreModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreModel record);

    int updateByPrimaryKey(StoreModel record);

    List<StoreModel> search(StoreModel record);
}