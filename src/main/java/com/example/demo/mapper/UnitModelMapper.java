package com.example.demo.mapper;

import com.example.demo.model.UnitModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UnitModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UnitModel record);

    int insertSelective(UnitModel record);

    UnitModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UnitModel record);

    int updateByPrimaryKey(UnitModel record);

    List<UnitModel> search(UnitModel record);

    List<UnitModel> selectByIdList(@Param("idList") List<Integer> idList);
}