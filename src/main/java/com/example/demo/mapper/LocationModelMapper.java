package com.example.demo.mapper;

import com.example.demo.model.LocationModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LocationModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocationModel record);

    int insertSelective(LocationModel record);

    LocationModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LocationModel record);

    int updateByPrimaryKey(LocationModel record);

    LocationModel getLocation(Integer userId);
}