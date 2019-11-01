package com.example.demo.mapper;

import com.example.demo.model.ActivityModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActivityModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityModel record);

    int insertSelective(ActivityModel record);

    ActivityModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityModel record);

    int updateByPrimaryKey(ActivityModel record);

    List<ActivityModel> search(ActivityModel record);
}