package com.example.demo.mapper;

import com.example.demo.model.SpecModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpecModel record);

    int insertSelective(SpecModel record);

    SpecModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecModel record);

    int updateByPrimaryKey(SpecModel record);

    List<SpecModel> search(SpecModel record);

    List<SpecModel> getByIds(List<Integer> ids);
}