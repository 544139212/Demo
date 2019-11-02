package com.example.demo.mapper;

import com.example.demo.model.NoticeModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeModel record);

    int insertSelective(NoticeModel record);

    NoticeModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeModel record);

    int updateByPrimaryKey(NoticeModel record);

    List<NoticeModel> search(NoticeModel record);
}