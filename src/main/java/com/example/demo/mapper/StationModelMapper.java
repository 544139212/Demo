package com.example.demo.mapper;

import com.example.demo.model.StationModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StationModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StationModel record);

    int insertSelective(StationModel record);

    StationModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StationModel record);

    int updateByPrimaryKey(StationModel record);

    List<StationModel> search(StationModel record);

    List<StationModel> selectByIdList(@Param("idList") List<Integer> idList);
}