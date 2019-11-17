package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.SpecValueMapper;
import com.example.demo.model.SpecValueModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.SpecValue;
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
@RequestMapping(value = "/specValue")
public class SpecValueController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    SpecValueMapper specValueMapper;

    /**
     * 添加规格值信息<>管理</>
     * @param specValue
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid SpecValue specValue) {
        logger.debug("参数:{}", JsonUtils.deserializer(specValue));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        SpecValueModel criteria = new SpecValueModel();
        criteria.setValue(specValue.getValue());
        criteria.setSpecId(specValue.getSpecId());
        List<SpecValueModel> list = specValueMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        SpecValueModel specValueModel = new SpecValueModel();
        BeanUtils.copyProperties(specValue, specValueModel);
        specValueModel.setCreateBy(Context.get().getUserId().toString());
        specValueModel.setCreateDate(new Date());
        specValueModel.setUpdateBy(Context.get().getUserId().toString());
        specValueModel.setUpdateDate(new Date());
        specValueMapper.insertSelective(specValueModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新规格值信息<>管理</>
     * @param specValue
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid SpecValue specValue) {
        logger.debug("参数:{}", JsonUtils.deserializer(specValue));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (specValue.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        SpecValueModel criteria = new SpecValueModel();
        criteria.setValue(specValue.getValue());
        criteria.setSpecId(specValue.getSpecId());
        List<SpecValueModel> list = specValueMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(specValue.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        SpecValueModel specValueModel = specValueMapper.selectByPrimaryKey(specValue.getId());
        if (specValueModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(specValue, specValueModel);
        specValueModel.setUpdateBy(Context.get().getUserId().toString());
        specValueModel.setUpdateDate(new Date());
        specValueMapper.updateByPrimaryKeySelective(specValueModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取规格值信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<SpecValue> get(@PathVariable Integer id) {
        Result<SpecValue> result = new Result<>();
        SpecValueModel criteria = new SpecValueModel();
        criteria.setId(id);
        List<SpecValueModel> list = specValueMapper.search(criteria);//TODO:优化
        SpecValue specValue = null;
        if (list != null && !list.isEmpty()) {
            specValue = new SpecValue();
            BeanUtils.copyProperties(list.get(0), specValue);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(specValue);
        return result;
    }

    /**
     * 获取全部规格值<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<SpecValue>> list(@RequestParam(value = "specId", required = false) Integer specId) {
        Result<List<SpecValue>> result = new Result<>();
        SpecValueModel criteria = new SpecValueModel();
        criteria.setSpecId(specId);
        List<SpecValueModel> list = specValueMapper.search(criteria);//TODO:优化
        List<SpecValue> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(specValueModel -> {
                SpecValue specValue = new SpecValue();
                BeanUtils.copyProperties(specValueModel, specValue);
                list1.add(specValue);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页规格值<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{specId}/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<SpecValue>> list(@PathVariable Integer specId, @PathVariable Integer pageNum) {
        Result<Pagination<SpecValue>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        SpecValueModel criteria = new SpecValueModel();
        criteria.setSpecId(specId);
        Page<SpecValueModel> page = (Page<SpecValueModel>) specValueMapper.search(criteria);//TODO:优化
        List<SpecValue> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(specValueModel -> {
                SpecValue specValue = new SpecValue();
                BeanUtils.copyProperties(specValueModel, specValue);
                list.add(specValue);
            });
        }
        Pagination<SpecValue> pagination = new Pagination<>();
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
