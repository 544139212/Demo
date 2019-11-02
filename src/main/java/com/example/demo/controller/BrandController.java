package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.BrandModelMapper;
import com.example.demo.model.BrandModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Brand;
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

@RestController
@RequestMapping(value = "/brand")
public class BrandController {
    private static Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    BrandModelMapper brandModelMapper;

    /**
     * 添加品牌信息<>管理</>
     * @param brand
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Brand brand) {
        logger.debug("参数:{}", JsonUtils.deserializer(brand));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        BrandModel criteria = new BrandModel();
        criteria.setName(brand.getName());
        List<BrandModel> list = brandModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        BrandModel brandModel = new BrandModel();
        BeanUtils.copyProperties(brand, brandModel);
        brandModel.setCreateBy(Context.get().getUserId().toString());
        brandModel.setCreateDate(new Date());
        brandModel.setUpdateBy(Context.get().getUserId().toString());
        brandModel.setUpdateDate(new Date());
        brandModelMapper.insertSelective(brandModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新品牌信息<>管理</>
     * @param brand
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Brand brand) {
        logger.debug("参数:{}", JsonUtils.deserializer(brand));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (brand.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        BrandModel criteria = new BrandModel();
        criteria.setName(brand.getName());
        List<BrandModel> list = brandModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(brand.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        BrandModel brandModel = brandModelMapper.selectByPrimaryKey(brand.getId());
        if (brandModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(brand, brandModel);
        brandModel.setUpdateBy(Context.get().getUserId().toString());
        brandModel.setUpdateDate(new Date());
        brandModelMapper.updateByPrimaryKeySelective(brandModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取品牌信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Brand> get(@PathVariable Integer id) {
        Result<Brand> result = new Result<>();
        BrandModel criteria = new BrandModel();
        criteria.setId(id);
        List<BrandModel> list = brandModelMapper.search(criteria);//TODO:优化
        Brand brand = null;
        if (list != null && !list.isEmpty()) {
            brand = new Brand();
            BeanUtils.copyProperties(list.get(0), brand);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(brand);
        return result;
    }

    /**
     * 获取全部品牌<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Brand>> list() {
        Result<List<Brand>> result = new Result<>();
        BrandModel criteria = new BrandModel();
        List<BrandModel> list = brandModelMapper.search(criteria);//TODO:优化
        List<Brand> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(brandModel -> {
                Brand brand = new Brand();
                BeanUtils.copyProperties(brandModel, brand);
                list1.add(brand);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页品牌<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Brand>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Brand>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        BrandModel criteria = new BrandModel();
        Page<BrandModel> page = (Page<BrandModel>)brandModelMapper.search(criteria);//TODO:优化
        List<Brand> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(brandModel -> {
                Brand brand = new Brand();
                BeanUtils.copyProperties(brandModel, brand);
                list.add(brand);
            });
        }
        Pagination<Brand> pagination = new Pagination<>();
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
}
