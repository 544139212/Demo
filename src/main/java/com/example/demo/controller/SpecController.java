package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.SpecMapper;
import com.example.demo.mapper.SpecValueMapper;
import com.example.demo.model.SpecModel;
import com.example.demo.model.SpecValueModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/spec")
public class SpecController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    SpecMapper specMapper;

    @Autowired
    SpecValueMapper specValueMapper;

    /**
     * 添加规格信息<>管理</>
     * @param spec
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Spec spec) {
        logger.debug("参数:{}", JsonUtils.deserializer(spec));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        SpecModel criteria = new SpecModel();
        criteria.setName(spec.getName());
        criteria.setCategoryId(spec.getCategoryId());
        List<SpecModel> list = specMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        SpecModel specModel = new SpecModel();
        BeanUtils.copyProperties(spec, specModel);
        specModel.setCreateBy(Context.get().getUserId().toString());
        specModel.setCreateDate(new Date());
        specModel.setUpdateBy(Context.get().getUserId().toString());
        specModel.setUpdateDate(new Date());
        int id = specMapper.insertSelective(specModel);

        if (!CollectionUtils.isEmpty(spec.getSpecValueList())) {
            spec.getSpecValueList().forEach(source -> {
                SpecValueModel target = new SpecValueModel();
                BeanUtils.copyProperties(source, target);
                target.setSpecId(id);
                target.setCreateBy(Context.get().getUserId().toString());
                target.setCreateDate(new Date());
                target.setUpdateBy(Context.get().getUserId().toString());
                target.setUpdateDate(new Date());
                specValueMapper.insertSelective(target);
            });
        }

        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新规格信息<>管理</>
     * @param spec
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Spec spec) {
        logger.debug("参数:{}", JsonUtils.deserializer(spec));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (spec.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        SpecModel criteria = new SpecModel();
        criteria.setName(spec.getName());
        criteria.setCategoryId(spec.getCategoryId());
        List<SpecModel> list = specMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(spec.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        SpecModel specModel = specMapper.selectByPrimaryKey(spec.getId());
        if (specModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(spec, specModel);
        specModel.setUpdateBy(Context.get().getUserId().toString());
        specModel.setUpdateDate(new Date());
        specMapper.updateByPrimaryKeySelective(specModel);
        if (!CollectionUtils.isEmpty(spec.getSpecValueList())) {
            List<Integer> ids = spec.getSpecValueList().parallelStream()
                    .filter(specValue -> Objects.nonNull(specValue.getId()))
                    .map(specValue -> specValue.getId()).collect(Collectors.toList());
            specValueMapper.disableByFKAndIds(spec.getId(), ids);
            spec.getSpecValueList().forEach(specValue -> {
                SpecValueModel specValueModel = new SpecValueModel();
                BeanUtils.copyProperties(specValue, specValueModel);
                specValueModel.setSpecId(spec.getId());
                specValueModel.setUpdateBy(Context.get().getUserId().toString());
                specValueModel.setUpdateDate(new Date());
                if (Objects.isNull(specValueModel.getId())) {
                    specValueModel.setCreateBy(Context.get().getUserId().toString());
                    specValueModel.setCreateDate(new Date());
                    specValueMapper.insertSelective(specValueModel);
                } else {
                    specValueMapper.updateByPrimaryKeySelective(specValueModel);
                }
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取规格信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Spec> get(@PathVariable Integer id) {
        Result<Spec> result = new Result<>();
        SpecModel criteria = new SpecModel();
        criteria.setId(id);
        List<SpecModel> list = specMapper.search(criteria);//TODO:优化
        Spec spec = null;
        if (list != null && !list.isEmpty()) {
            spec = new Spec();
            BeanUtils.copyProperties(list.get(0), spec);
            SpecValueModel specValueModel = new SpecValueModel();
            specValueModel.setSpecId(id);
            List<SpecValueModel> specValueModelList = specValueMapper.search(specValueModel);
            List<SpecValue> specValueList= new ArrayList<>();
            if (!CollectionUtils.isEmpty(specValueModelList)) {
                specValueModelList.stream().forEach(source -> {
                    SpecValue target = new SpecValue();
                    BeanUtils.copyProperties(source, target);
                    specValueList.add(target);
                });
            }
            spec.setSpecValueList(specValueList);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(spec);
        return result;
    }

    /**
     * 获取全部规格<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Spec>> list(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        Result<List<Spec>> result = new Result<>();
        SpecModel criteria = new SpecModel();
        criteria.setCategoryId(categoryId);
        List<SpecModel> list = specMapper.search(criteria);//TODO:优化
        List<Spec> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            List<Integer> specIds = list.parallelStream().map(specModel -> specModel.getId()).collect(Collectors.toList());
            List<SpecValueModel> specValueModelList = specValueMapper.getBySpecIds(specIds);
            Map<Integer, List<SpecValueModel>> specValueModelListMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(specValueModelList)) {
                specValueModelListMap = specValueModelList.stream().collect(Collectors.groupingBy(SpecValueModel::getSpecId));
            }
            for (SpecModel specModel : list) {
                Spec spec = new Spec();
                BeanUtils.copyProperties(specModel, spec);
                List<SpecValueModel> specValueModelList1 = specValueModelListMap.get(specModel.getId());
                List<SpecValue> specValueList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(specValueModelList1)) {
                    specValueModelList1.stream().forEach(source -> {
                        SpecValue target = new SpecValue();
                        BeanUtils.copyProperties(source, target);
                        specValueList.add(target);
                    });
                }
                spec.setSpecValueList(specValueList);
                list1.add(spec);
            }
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页规格<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{categoryId}/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Spec>> list(@PathVariable Integer categoryId, @PathVariable Integer pageNum) {
        Result<Pagination<Spec>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        SpecModel criteria = new SpecModel();
        criteria.setCategoryId(categoryId);
        Page<SpecModel> page = (Page<SpecModel>) specMapper.search(criteria);//TODO:优化
        List<Spec> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(specModel -> {
                Spec spec = new Spec();
                BeanUtils.copyProperties(specModel, spec);
                list.add(spec);
            });
        }
        Pagination<Spec> pagination = new Pagination<>();
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
