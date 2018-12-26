package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.UnitModelMapper;
import com.example.demo.model.UnitModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Unit;
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
@RequestMapping(value = "/unit")
public class UnitController {
    private static Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Autowired
    UnitModelMapper unitModelMapper;

    /**
     * 添加单位信息<>管理</>
     * @param unit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Unit unit) {
        logger.debug("参数:{}", JsonUtils.deserializer(unit));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        UnitModel criteria = new UnitModel();
        criteria.setName(unit.getName());
        List<UnitModel> list = unitModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        UnitModel unitModel = new UnitModel();
        BeanUtils.copyProperties(unit, unitModel);
        unitModel.setCreateBy(Context.get().getUserId().toString());
        unitModel.setCreateDate(new Date());
        unitModel.setUpdateBy(Context.get().getUserId().toString());
        unitModel.setUpdateDate(new Date());
        unitModelMapper.insertSelective(unitModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新单位信息<>管理</>
     * @param unit
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Unit unit) {
        logger.debug("参数:{}", JsonUtils.deserializer(unit));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (unit.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        UnitModel criteria = new UnitModel();
        criteria.setName(unit.getName());
        List<UnitModel> list = unitModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(unit.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        UnitModel unitModel = unitModelMapper.selectByPrimaryKey(unit.getId());
        if (unitModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(unit, unitModel);
        unitModel.setUpdateBy(Context.get().getUserId().toString());
        unitModel.setUpdateDate(new Date());
        unitModelMapper.updateByPrimaryKeySelective(unitModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取单位信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Unit> get(@PathVariable Integer id) {
        Result<Unit> result = new Result<>();
        UnitModel criteria = new UnitModel();
        criteria.setId(id);
        List<UnitModel> list = unitModelMapper.search(criteria);//TODO:优化
        Unit unit = null;
        if (list != null && !list.isEmpty()) {
            unit = new Unit();
            BeanUtils.copyProperties(list.get(0), unit);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(unit);
        return result;
    }

    /**
     * 获取全部单位<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Unit>> list() {
        Result<List<Unit>> result = new Result<>();
        UnitModel criteria = new UnitModel();
        List<UnitModel> list = unitModelMapper.search(criteria);//TODO:优化
        List<Unit> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(unitModel -> {
                Unit unit = new Unit();
                BeanUtils.copyProperties(unitModel, unit);
                list1.add(unit);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页单位<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Unit>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Unit>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        UnitModel criteria = new UnitModel();
        Page<UnitModel> page = (Page<UnitModel>)unitModelMapper.search(criteria);//TODO:优化
        List<Unit> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(unitModel -> {
                Unit unit = new Unit();
                BeanUtils.copyProperties(unitModel, unit);
                list.add(unit);
            });
        }
        Pagination<Unit> pagination = new Pagination<>();
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
