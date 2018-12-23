package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.enums.AuditStatusEnum;
import com.example.demo.enums.StoreLockStatusEnum;
import com.example.demo.enums.StoreOpenStatusEnum;
import com.example.demo.mapper.StoreModelMapper;
import com.example.demo.model.StoreModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Store;
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
@RequestMapping(value = "/store")
public class StoreController {

    private static Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    StoreModelMapper storeModelMapper;

    /**
     * 添加店铺信息<>当前登录用户</>
     * @param store
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Store store) {
        logger.debug("参数:{}", JsonUtils.deserializer(store));

        Result<Void> result = new Result<>();
        StoreModel criteria = new StoreModel();
        criteria.setUserId(Context.get().getUserId());
        List<StoreModel> list = storeModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        StoreModel storeModel = new StoreModel();
        BeanUtils.copyProperties(store, storeModel);
        storeModel.setUserId(Context.get().getUserId());
        storeModel.setOpenStatus(StoreOpenStatusEnum.CLOSE.getCode());
        storeModel.setAuditStatus(AuditStatusEnum.PENDING.getCode());
        storeModel.setLockStatus(StoreLockStatusEnum.LOCK.getCode());
        storeModel.setCreateBy(Context.get().getUserId().toString());
        storeModel.setCreateDate(new Date());
        storeModel.setUpdateBy(Context.get().getUserId().toString());
        storeModel.setUpdateDate(new Date());
        storeModelMapper.insertSelective(storeModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新店铺信息<>当前登录用户</>
     * @param store
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Store store) {
        logger.debug("参数:{}", JsonUtils.deserializer(store));

        Result<Void> result = new Result<>();
        if (store.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        StoreModel storeModel = storeModelMapper.selectByPrimaryKey(store.getId());
        if (storeModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        if (!storeModel.getUserId().equals(Context.get().getUserId())) {
            result.setCode(ResponseStatusEnum.AUTH.getCode());
            result.setMsg(ResponseStatusEnum.AUTH.getMsg());
            return result;
        }
        BeanUtils.copyProperties(store, storeModel);
        storeModel.setUpdateBy(Context.get().getUserId().toString());
        storeModel.setUpdateDate(new Date());
        storeModelMapper.updateByPrimaryKeySelective(storeModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取店铺信息<>当前登录用户</>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<Store> get() {
        Result<Store> result = new Result<>();
        StoreModel criteria = new StoreModel();
        criteria.setUserId(Context.get().getUserId());
        List<StoreModel> list = storeModelMapper.search(criteria);//TODO:优化
        Store store = null;
        if (list != null && !list.isEmpty()) {
            store = new Store();
            BeanUtils.copyProperties(list.get(0), store);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(store);
        return result;
    }

    /**
     * 获取店铺信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Store> get(@PathVariable Integer id) {
        Result<Store> result = new Result<>();
        StoreModel criteria = new StoreModel();
        criteria.setId(id);
        List<StoreModel> list = storeModelMapper.search(criteria);//TODO:优化
        Store store = null;
        if (list != null && !list.isEmpty()) {
            store = new Store();
            BeanUtils.copyProperties(list.get(0), store);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(store);
        return result;
    }

    /**
     * 获取分页店铺<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Store>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Store>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        StoreModel criteria = new StoreModel();
        Page<StoreModel> page = (Page<StoreModel>)storeModelMapper.search(criteria);//TODO:优化
        List<Store> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(storeModel -> {
                Store store = new Store();
                BeanUtils.copyProperties(storeModel, store);
                list.add(store);
            });
        }
        Pagination<Store> pagination = new Pagination<>();
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
