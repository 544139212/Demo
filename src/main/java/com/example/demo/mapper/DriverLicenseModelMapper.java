package com.example.demo.mapper;

import com.example.demo.model.DriverLicenseModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DriverLicenseModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverLicenseModel record);

    int insertSelective(DriverLicenseModel record);

    DriverLicenseModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverLicenseModel record);

    int updateByPrimaryKey(DriverLicenseModel record);

    List<DriverLicenseModel> search(DriverLicenseModel record);
}