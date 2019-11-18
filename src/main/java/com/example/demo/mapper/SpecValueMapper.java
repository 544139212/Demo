package com.example.demo.mapper;

import com.example.demo.model.SpecModel;
import com.example.demo.model.SpecValueModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpecValueModel record);

    int insertSelective(SpecValueModel record);

    SpecValueModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecValueModel record);

    int updateByPrimaryKey(SpecValueModel record);

    List<SpecValueModel> search(SpecValueModel record);

    int disableByFKAndIds(@Param("specId") Integer specId, @Param("ids") List<Integer> ids);

    List<SpecValueModel> getBySpecIds(@Param("specIds") List<Integer> specIds);
}