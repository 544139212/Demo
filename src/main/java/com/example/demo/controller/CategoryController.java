package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.CategoryModelMapper;
import com.example.demo.mapper.CategorySpecMapper;
import com.example.demo.model.CategoryModel;
import com.example.demo.model.CategorySpecModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Category;
import com.example.demo.vo.CategorySpec;
import com.example.demo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    CategoryModelMapper categoryModelMapper;

    @Autowired
    CategorySpecMapper categorySpecMapper;

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
        if ((category.getParentId() == 0 && (!CollectionUtils.isEmpty(category.getCategorySpecList()) || !StringUtils.isEmpty(category.getImage())))
                || (category.getParentId() > 0 && (CollectionUtils.isEmpty(category.getCategorySpecList()) || StringUtils.isEmpty(category.getImage())))) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
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
        int id = categoryModelMapper.insertSelective(categoryModel);
        if (!CollectionUtils.isEmpty(category.getCategorySpecList())) {
            category.getCategorySpecList().forEach(source -> {
                CategorySpecModel target = new CategorySpecModel();
                BeanUtils.copyProperties(source, target);
                target.setCategoryId(id);
                target.setCreateBy(Context.get().getUserId().toString());
                target.setCreateDate(new Date());
                target.setUpdateBy(Context.get().getUserId().toString());
                target.setUpdateDate(new Date());
                categorySpecMapper.insertSelective(target);
            });
        }
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
        if (category.getId() == null
                || (category.getParentId() == 0 && (!CollectionUtils.isEmpty(category.getCategorySpecList()) || !StringUtils.isEmpty(category.getImage())))
                || (category.getParentId() > 0 && (CollectionUtils.isEmpty(category.getCategorySpecList()) || StringUtils.isEmpty(category.getImage())))) {
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
        if (!CollectionUtils.isEmpty(category.getCategorySpecList())) {
            List<Integer> ids = category.getCategorySpecList().parallelStream()
                    .filter(categorySpec -> Objects.nonNull(categorySpec.getId()))
                    .map(categorySpec -> categorySpec.getId()).collect(Collectors.toList());
            categorySpecMapper.disableByFKAndIds(category.getId(), ids);
            category.getCategorySpecList().forEach(categorySpec -> {
                CategorySpecModel categorySpecModel = new CategorySpecModel();
                BeanUtils.copyProperties(categorySpec, categorySpecModel);
                categorySpecModel.setCategoryId(category.getId());
                categorySpecModel.setUpdateBy(Context.get().getUserId().toString());
                categorySpecModel.setUpdateDate(new Date());
                if (Objects.isNull(categorySpecModel.getId())) {
                    categorySpecModel.setCreateBy(Context.get().getUserId().toString());
                    categorySpecModel.setCreateDate(new Date());
                    categorySpecMapper.insertSelective(categorySpecModel);
                } else {
                    categorySpecMapper.updateByPrimaryKeySelective(categorySpecModel);
                }
            });
        }
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
            CategorySpecModel categorySpecModel = new CategorySpecModel();
            categorySpecModel.setCategoryId(id);
            List<CategorySpecModel> categorySpecModelList = categorySpecMapper.search(categorySpecModel);
            List<CategorySpec> categorySpecList= new ArrayList<>();
            if (!CollectionUtils.isEmpty(categorySpecModelList)) {
                categorySpecModelList.stream().forEach(source -> {
                    CategorySpec target = new CategorySpec();
                    BeanUtils.copyProperties(source, target);
                    categorySpecList.add(target);
                });
            }
            category.setCategorySpecList(categorySpecList);
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
    public Result<List<Category>> list(@RequestParam(value = "parentId", required = false) Integer parentId) {
        Result<List<Category>> result = new Result<>();
        CategoryModel criteria = new CategoryModel();
        criteria.setParentId(parentId);
        List<CategoryModel> list = categoryModelMapper.search(criteria);//TODO:优化
        List<Category> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(categoryModel -> {
                Category category = new Category();
                BeanUtils.copyProperties(categoryModel, category);
                list1.add(category);
            });
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
    /*@RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Category>> page(@PathVariable Integer pageNum) {
        Result<Pagination<Category>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        CategoryModel criteria = new CategoryModel();
        Page<CategoryModel> page = (Page<CategoryModel>)categoryModelMapper.search(criteria);//TODO:优化
        List<Category> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(categoryModel -> {
                Category category = new Category();
                BeanUtils.copyProperties(categoryModel, category);
                list.add(category);
            });
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
    }*/
}
