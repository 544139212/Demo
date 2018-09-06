package com.example.demo.controller;

import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.AttributeModelMapper;
import com.example.demo.model.AttributeModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Attribute;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/attribute")
public class AttributeController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    AttributeModelMapper attributeModelMapper;

    /**
     * 添加站点信息<>管理</>
     * @param attribute
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Attribute attribute) {
        logger.debug("参数:{}", JsonUtils.deserializer(attribute));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        AttributeModel criteria = new AttributeModel();
        criteria.setName(attribute.getName());
        List<AttributeModel> list = attributeModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        AttributeModel attributeModel = new AttributeModel();
        BeanUtils.copyProperties(attribute, attributeModel);
        attributeModelMapper.insertSelective(attributeModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新站点信息<>管理</>
     * @param attribute
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Attribute attribute) {
        logger.debug("参数:{}", JsonUtils.deserializer(attribute));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (attribute.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        AttributeModel criteria = new AttributeModel();
        criteria.setName(attribute.getName());
        List<AttributeModel> list = attributeModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(attribute.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        AttributeModel attributeModel = attributeModelMapper.selectByPrimaryKey(attribute.getId());
        if (attributeModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(attribute, attributeModel);
        attributeModelMapper.updateByPrimaryKeySelective(attributeModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取站点信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Attribute> get(@PathVariable Integer id) {
        Result<Attribute> result = new Result<>();
        AttributeModel criteria = new AttributeModel();
        criteria.setId(id);
        List<AttributeModel> list = attributeModelMapper.search(criteria);//TODO:优化
        Attribute attribute = null;
        if (list != null && !list.isEmpty()) {
            attribute = new Attribute();
            BeanUtils.copyProperties(list.get(0), attribute);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(attribute);
        return result;
    }

    /**
     * 获取全部站点<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Attribute>> list() {
        Result<List<Attribute>> result = new Result<>();
        AttributeModel criteria = new AttributeModel();
        List<AttributeModel> list = attributeModelMapper.search(criteria);//TODO:优化
        List<Attribute> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(attributeModel -> {
                Attribute attribute = new Attribute();
                BeanUtils.copyProperties(attributeModel, attribute);
                list1.add(attribute);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页站点<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Attribute>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Attribute>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        AttributeModel criteria = new AttributeModel();
        Page<AttributeModel> page = (Page<AttributeModel>)attributeModelMapper.search(criteria);//TODO:优化
        List<Attribute> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(attributeModel -> {
                Attribute attribute = new Attribute();
                BeanUtils.copyProperties(attributeModel, attribute);
                list.add(attribute);
            });
        }
        Pagination<Attribute> pagination = new Pagination<>();
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
