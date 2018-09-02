package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.ShopModelMapper;
import com.example.demo.model.ShopModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Result;
import com.example.demo.vo.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/shop")
public class ShopController {

    private static Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    ShopModelMapper shopModelMapper;

    /**
     * 添加店铺信息<>当前登录用户</>
     * @param shop
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Shop shop) {
        logger.debug("参数:{}", JsonUtils.deserializer(shop));

        Result<Void> result = new Result<>();
        ShopModel criteria = new ShopModel();
        criteria.setUserId(Context.get().getUserId());
        List<ShopModel> list = shopModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        ShopModel shopModel = new ShopModel();
        BeanUtils.copyProperties(shop, shopModel);
        shopModel.setUserId(Context.get().getUserId());
        shopModelMapper.insertSelective(shopModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新店铺信息<>当前登录用户</>
     * @param shop
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Shop shop) {
        logger.debug("参数:{}", JsonUtils.deserializer(shop));

        Result<Void> result = new Result<>();
        if (shop.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        ShopModel shopModel = shopModelMapper.selectByPrimaryKey(shop.getId());
        if (shopModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        if (!shopModel.getUserId().equals(Context.get().getUserId())) {
            result.setCode(ResponseStatusEnum.AUTH.getCode());
            result.setMsg(ResponseStatusEnum.AUTH.getMsg());
            return result;
        }
        BeanUtils.copyProperties(shop, shopModel);
        shopModelMapper.updateByPrimaryKeySelective(shopModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取店铺信息<>当前登录用户</>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<Shop> get() {
        Result<Shop> result = new Result<>();
        ShopModel criteria = new ShopModel();
        criteria.setUserId(Context.get().getUserId());
        List<ShopModel> list = shopModelMapper.search(criteria);//TODO:优化
        Shop shop = null;
        if (list != null && !list.isEmpty()) {
            shop = new Shop();
            BeanUtils.copyProperties(list.get(0), shop);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(shop);
        return result;
    }

    /**
     * 获取店铺信息<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Shop> get(@PathVariable Integer id) {
        Result<Shop> result = new Result<>();
        ShopModel criteria = new ShopModel();
        criteria.setId(id);
        List<ShopModel> list = shopModelMapper.search(criteria);//TODO:优化
        Shop shop = null;
        if (list != null && !list.isEmpty()) {
            shop = new Shop();
            BeanUtils.copyProperties(list.get(0), shop);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(shop);
        return result;
    }
}
