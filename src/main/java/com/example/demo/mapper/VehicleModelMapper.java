package com.example.demo.mapper;

import com.example.demo.model.VehicleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VehicleModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VehicleModel record);

    int insertSelective(VehicleModel record);

    VehicleModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VehicleModel record);

    int updateByPrimaryKey(VehicleModel record);

    List<VehicleModel> search(VehicleModel record);

    List<VehicleModel> selectByUserIdList(@Param("userIdList") List<Integer> userIdList);
}