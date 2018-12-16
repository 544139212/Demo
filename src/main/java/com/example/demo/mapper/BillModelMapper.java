package com.example.demo.mapper;

import com.example.demo.model.BillModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BillModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillModel record);

    int insertSelective(BillModel record);

    BillModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillModel record);

    int updateByPrimaryKey(BillModel record);

    List<BillModel> search(BillModel record);
}