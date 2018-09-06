package com.example.demo.mapper;

import com.example.demo.model.UnitModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UnitModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UnitModel record);

    int insertSelective(UnitModel record);

    UnitModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UnitModel record);

    int updateByPrimaryKey(UnitModel record);
}