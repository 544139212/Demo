package com.example.demo.mapper;

import com.example.demo.model.BrandModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BrandModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrandModel record);

    int insertSelective(BrandModel record);

    BrandModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrandModel record);

    int updateByPrimaryKey(BrandModel record);

    List<BrandModel> search(BrandModel record);
}