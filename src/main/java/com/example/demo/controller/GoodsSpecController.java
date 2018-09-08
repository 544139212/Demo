package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.GoodsSpecModelMapper;
import com.example.demo.model.GoodsSpecModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.GoodsSpec;
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
@RequestMapping(value = "/goodsSpec")
public class GoodsSpecController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    GoodsSpecModelMapper goodsSpecModelMapper;

    /**
     * 添加商品规格信息<>管理</>
     * @param goodsSpec
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid GoodsSpec goodsSpec) {
        logger.debug("参数:{}", JsonUtils.deserializer(goodsSpec));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        GoodsSpecModel criteria = new GoodsSpecModel();
        criteria.setName(goodsSpec.getName());
        List<GoodsSpecModel> list = goodsSpecModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        GoodsSpecModel goodsSpecModel = new GoodsSpecModel();
        BeanUtils.copyProperties(goodsSpec, goodsSpecModel);
        goodsSpecModel.setCreateBy(Context.get().getUserId().toString());
        goodsSpecModel.setCreateDate(new Date());
        goodsSpecModel.setUpdateBy(Context.get().getUserId().toString());
        goodsSpecModel.setUpdateDate(new Date());
        goodsSpecModelMapper.insertSelective(goodsSpecModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新商品规格信息<>管理</>
     * @param goodsSpec
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid GoodsSpec goodsSpec) {
        logger.debug("参数:{}", JsonUtils.deserializer(goodsSpec));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (goodsSpec.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        GoodsSpecModel criteria = new GoodsSpecModel();
        criteria.setName(goodsSpec.getName());
        List<GoodsSpecModel> list = goodsSpecModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(goodsSpec.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        GoodsSpecModel goodsSpecModel = goodsSpecModelMapper.selectByPrimaryKey(goodsSpec.getId());
        if (goodsSpecModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(goodsSpec, goodsSpecModel);
        goodsSpecModel.setUpdateBy(Context.get().getUserId().toString());
        goodsSpecModel.setUpdateDate(new Date());
        goodsSpecModelMapper.updateByPrimaryKeySelective(goodsSpecModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取商品规格信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<GoodsSpec> get(@PathVariable Integer id) {
        Result<GoodsSpec> result = new Result<>();
        GoodsSpecModel criteria = new GoodsSpecModel();
        criteria.setId(id);
        List<GoodsSpecModel> list = goodsSpecModelMapper.search(criteria);//TODO:优化
        GoodsSpec goodsSpec = null;
        if (list != null && !list.isEmpty()) {
            goodsSpec = new GoodsSpec();
            BeanUtils.copyProperties(list.get(0), goodsSpec);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(goodsSpec);
        return result;
    }

    /**
     * 获取全部商品规格<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<GoodsSpec>> list() {
        Result<List<GoodsSpec>> result = new Result<>();
        GoodsSpecModel criteria = new GoodsSpecModel();
        List<GoodsSpecModel> list = goodsSpecModelMapper.search(criteria);//TODO:优化
        List<GoodsSpec> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(goodsSpecModel -> {
                GoodsSpec goodsSpec = new GoodsSpec();
                BeanUtils.copyProperties(goodsSpecModel, goodsSpec);
                list1.add(goodsSpec);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页商品规格<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<GoodsSpec>> list(@PathVariable Integer pageNum) {
        Result<Pagination<GoodsSpec>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        GoodsSpecModel criteria = new GoodsSpecModel();
        Page<GoodsSpecModel> page = (Page<GoodsSpecModel>)goodsSpecModelMapper.search(criteria);//TODO:优化
        List<GoodsSpec> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(goodsSpecModel -> {
                GoodsSpec goodsSpec = new GoodsSpec();
                BeanUtils.copyProperties(goodsSpecModel, goodsSpec);
                list.add(goodsSpec);
            });
        }
        Pagination<GoodsSpec> pagination = new Pagination<>();
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
