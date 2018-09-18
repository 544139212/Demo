package com.example.demo.mapper;

import com.example.demo.model.BroadcastModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BroadcastModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BroadcastModel record);

    int insertSelective(BroadcastModel record);

    BroadcastModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BroadcastModel record);

    int updateByPrimaryKeyWithBLOBs(BroadcastModel record);

    int updateByPrimaryKey(BroadcastModel record);

    List<BroadcastModel> search(BroadcastModel record);
}