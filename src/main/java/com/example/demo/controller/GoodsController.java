package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.AuditStatusEnum;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.enums.SaleStatusEnum;
import com.example.demo.mapper.GoodsModelMapper;
import com.example.demo.mapper.ShopModelMapper;
import com.example.demo.model.GoodsModel;
import com.example.demo.model.ShopModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Goods;
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
@RequestMapping(value = "/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    GoodsModelMapper goodsModelMapper;

    @Autowired
    ShopModelMapper shopModelMapper;

    /**
     * 添加商品信息<>当前登录用户</>
     * @param goods
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Goods goods) {
        logger.debug("参数:{}", JsonUtils.deserializer(goods));

        Result<Void> result = new Result<>();
        ShopModel criteria1 = new ShopModel();
        criteria1.setUserId(Context.get().getUserId());
        List<ShopModel> list1 = shopModelMapper.search(criteria1);//TODO:优化
        if (list1 == null || list1.isEmpty()) {
            result.setCode(ResponseStatusEnum.STORE_NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.STORE_NOT_FOUND.getMsg());
            return result;
        }

        GoodsModel criteria = new GoodsModel();
        criteria.setStoreId(list1.get(0).getId());
        criteria.setName(goods.getName());
        List<GoodsModel> list = goodsModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        GoodsModel goodsModel = new GoodsModel();
        BeanUtils.copyProperties(goods, goodsModel);
        goodsModel.setStoreId(list1.get(0).getId());
        goodsModel.setAuditStatus(AuditStatusEnum.PENDING.getCode());
        goodsModel.setSaleStatus(SaleStatusEnum.STOP_SALE.getCode());
        goodsModel.setCreateBy(Context.get().getUserId().toString());
        goodsModel.setCreateDate(new Date());
        goodsModel.setUpdateBy(Context.get().getUserId().toString());
        goodsModel.setUpdateDate(new Date());
        goodsModelMapper.insertSelective(goodsModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新商品信息<>当前登录用户</>
     * @param goods
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Goods goods) {
        logger.debug("参数:{}", JsonUtils.deserializer(goods));

        Result<Void> result = new Result<>();
        if (goods.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        GoodsModel goodsModel = goodsModelMapper.selectByPrimaryKey(goods.getId());
        if (goodsModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }

        ShopModel criteria1 = new ShopModel();
        criteria1.setUserId(Context.get().getUserId());
        List<ShopModel> list1 = shopModelMapper.search(criteria1);//TODO:优化
        if (list1 == null || list1.isEmpty()) {
            result.setCode(ResponseStatusEnum.STORE_NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.STORE_NOT_FOUND.getMsg());
            return result;
        }
        if (!goodsModel.getStoreId().equals(list1.get(0).getId())) {
            result.setCode(ResponseStatusEnum.AUTH.getCode());
            result.setMsg(ResponseStatusEnum.AUTH.getMsg());
            return result;
        }
        BeanUtils.copyProperties(goods, goodsModel);
        goodsModel.setUpdateBy(Context.get().getUserId().toString());
        goodsModel.setUpdateDate(new Date());
        goodsModelMapper.updateByPrimaryKeySelective(goodsModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取商品信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Goods> get(@PathVariable Integer id) {
        Result<Goods> result = new Result<>();
        GoodsModel criteria = new GoodsModel();
        criteria.setId(id);
        List<GoodsModel> list = goodsModelMapper.search(criteria);//TODO:优化
        Goods goods = null;
        if (list != null && !list.isEmpty()) {
            goods = new Goods();
            BeanUtils.copyProperties(list.get(0), goods);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(goods);
        return result;
    }

    /**
     * 获取分页商品<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Goods>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Goods>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        GoodsModel criteria = new GoodsModel();
        Page<GoodsModel> page = (Page<GoodsModel>)goodsModelMapper.search(criteria);//TODO:优化
        List<Goods> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(goodsModel -> {
                Goods goods = new Goods();
                BeanUtils.copyProperties(goodsModel, goods);
                list.add(goods);
            });
        }
        Pagination<Goods> pagination = new Pagination<>();
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
