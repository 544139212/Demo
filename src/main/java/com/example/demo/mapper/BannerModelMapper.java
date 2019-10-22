package com.example.demo.mapper;

import com.example.demo.model.BannerModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BannerModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BannerModel record);

    int insertSelective(BannerModel record);

    BannerModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BannerModel record);

    int updateByPrimaryKey(BannerModel record);

    List<BannerModel> search(BannerModel record);
}