package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.CategoryModelMapper;
import com.example.demo.model.CategoryModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Category;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    CategoryModelMapper categoryModelMapper;

    /**
     * 添加分类信息<>管理</>
     * @param category
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Category category) {
        logger.debug("参数:{}", JsonUtils.deserializer(category));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        CategoryModel criteria = new CategoryModel();
        criteria.setName(category.getName());
        List<CategoryModel> list = categoryModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(category, categoryModel);
        categoryModel.setCreateBy(Context.get().getUserId().toString());
        categoryModel.setCreateDate(new Date());
        categoryModel.setUpdateBy(Context.get().getUserId().toString());
        categoryModel.setUpdateDate(new Date());
        categoryModelMapper.insertSelective(categoryModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新分类信息<>管理</>
     * @param category
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Category category) {
        logger.debug("参数:{}", JsonUtils.deserializer(category));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (category.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        CategoryModel criteria = new CategoryModel();
        criteria.setName(category.getName());
        List<CategoryModel> list = categoryModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(category.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        CategoryModel categoryModel = categoryModelMapper.selectByPrimaryKey(category.getId());
        if (categoryModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(category, categoryModel);
        categoryModel.setUpdateBy(Context.get().getUserId().toString());
        categoryModel.setUpdateDate(new Date());
        categoryModelMapper.updateByPrimaryKeySelective(categoryModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取分类信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Category> get(@PathVariable Integer id) {
        Result<Category> result = new Result<>();
        CategoryModel criteria = new CategoryModel();
        criteria.setId(id);
        List<CategoryModel> list = categoryModelMapper.search(criteria);//TODO:优化
        Category category = null;
        if (list != null && !list.isEmpty()) {
            category = new Category();
            BeanUtils.copyProperties(list.get(0), category);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(category);
        return result;
    }

    /**
     * 获取全部分类<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Category>> list() {
        Result<List<Category>> result = new Result<>();
        CategoryModel criteria = new CategoryModel();
        List<CategoryModel> list = categoryModelMapper.search(criteria);//TODO:优化
        List<Category> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list1 = getChildren(0, list);
            /*list.stream().forEach(categoryModel -> {
                Category category = new Category();
                BeanUtils.copyProperties(categoryModel, category);
                list1.add(category);
            });*/
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页分类<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Category>> page(@PathVariable Integer pageNum) {
        Result<Pagination<Category>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        CategoryModel criteria = new CategoryModel();
        Page<CategoryModel> page = (Page<CategoryModel>)categoryModelMapper.search(criteria);//TODO:优化
        List<Category> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            list = getChildren(0, page.getResult());
            /*page.getResult().stream().forEach(categoryModel -> {
                Category category = new Category();
                BeanUtils.copyProperties(categoryModel, category);
                list.add(category);
            });*/
        }
        Pagination<Category> pagination = new Pagination<>();
        pagination.setPageNum(page.getPageNum());
        pagination.setPageSize(page.getPageSize());
        pagination.setPages(page.getPages());
        pagination.setTotal(page.getTotal());
        pagination.setList(list);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(pagination);
        return result;
    }

    private List<Category> getChildren(Integer parentId, List<CategoryModel> categoryList) {
        List<Category> list = new ArrayList<>();
        categoryList.stream().filter(categoryModel -> Objects.equals(categoryModel.getParentId(), parentId)).forEach(categoryModel -> {
            Category category = new Category();
            BeanUtils.copyProperties(categoryModel, category);
            List<Category> children = getChildren(categoryModel.getId(), categoryList);
            category.setChildren(children);
            list.add(category);
        });
        return list;
    }

    /**
     * 获取全部分类<>小程序商家专用</>
     * @return
     */
    @RequestMapping(value = "/array", method = RequestMethod.GET)
    public Result<List<Object>> array() {
        Result<List<Object>> result = new Result<>();
        CategoryModel criteria = new CategoryModel();
        List<CategoryModel> list = categoryModelMapper.search(criteria);//TODO:优化
        List<Object> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            List<Category> firstLevelList = new ArrayList<>();
            List<List<Category>> secondLevelList = new ArrayList<>();
            list.stream().forEach(categoryModel -> {
                Category category = new Category();
                BeanUtils.copyProperties(categoryModel, category);
                if (Objects.equals(category.getParentId(), 0)) {
                    firstLevelList.add(category);
                }
                if (!Objects.equals(category.getParentId(), 0)) {
                    if (secondLevelList.isEmpty() || !Objects.equals(category.getParentId(), secondLevelList.subList(secondLevelList.size() - 1, secondLevelList.size()).get(0).get(0).getParentId())) {
                        List<Category> secondLevelListElementList = new ArrayList<>();
                        secondLevelList.add(secondLevelListElementList);
                    }
                    secondLevelList.subList(secondLevelList.size() - 1, secondLevelList.size()).get(0).add(category);
                }
            });
            list1.add(firstLevelList);
            list1.add(secondLevelList);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }
}
