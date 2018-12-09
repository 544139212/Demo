package com.example.demo.mapper;

import com.example.demo.model.MessageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageModel record);

    int insertSelective(MessageModel record);

    MessageModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageModel record);

    int updateByPrimaryKey(MessageModel record);

    List<MessageModel> findMessages(@Param("ophone") String ophone, @Param("tphone") String tphone);

    List<MessageModel> findRecentContacts(@Param("ophone") String ophone);
}