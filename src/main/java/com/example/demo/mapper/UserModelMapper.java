package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

    List<UserModel> search(UserModel record);
}