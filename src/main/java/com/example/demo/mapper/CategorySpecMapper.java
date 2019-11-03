package com.example.demo.mapper;

import com.example.demo.model.CategorySpecModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategorySpecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategorySpecModel record);

    int insertSelective(CategorySpecModel record);

    CategorySpecModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategorySpecModel record);

    int updateByPrimaryKey(CategorySpecModel record);

    List<CategorySpecModel> search(CategorySpecModel record);

    int disableByFKAndIds(@Param("categoryId") Integer categoryId, @Param("ids") List<Integer> ids);
}