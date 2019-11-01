package com.example.demo.mapper;

import com.example.demo.model.ChannelModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChannelModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelModel record);

    int insertSelective(ChannelModel record);

    ChannelModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelModel record);

    int updateByPrimaryKey(ChannelModel record);

    List<ChannelModel> search(ChannelModel record);
}