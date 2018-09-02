package com.example.demo.mapper;

import com.example.demo.model.PlanModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlanModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlanModel record);

    int insertSelective(PlanModel record);

    PlanModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlanModel record);

    int updateByPrimaryKey(PlanModel record);

    List<PlanModel> search(PlanModel record);
}