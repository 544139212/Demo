package com.example.demo.mapper;

import com.example.demo.model.AttributeModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AttributeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AttributeModel record);

    int insertSelective(AttributeModel record);

    AttributeModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AttributeModel record);

    int updateByPrimaryKey(AttributeModel record);

    List<AttributeModel> search(AttributeModel record);
}