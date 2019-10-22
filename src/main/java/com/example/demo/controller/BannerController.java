package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.BannerModelMapper;
import com.example.demo.model.BannerModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Banner;
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
@RequestMapping(value = "/banner")
public class BannerController {
    private static Logger logger = LoggerFactory.getLogger(BannerController.class);

    @Autowired
    BannerModelMapper bannerModelMapper;

    /**
     * 添加轮播图信息<>管理</>
     * @param banner
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Banner banner) {
        logger.debug("参数:{}", JsonUtils.deserializer(banner));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        BannerModel criteria = new BannerModel();
        criteria.setTitle(banner.getTitle());
        List<BannerModel> list = bannerModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        BannerModel bannerModel = new BannerModel();
        BeanUtils.copyProperties(banner, bannerModel);
        bannerModel.setCreateBy(Context.get().getUserId().toString());
        bannerModel.setCreateDate(new Date());
        bannerModel.setUpdateBy(Context.get().getUserId().toString());
        bannerModel.setUpdateDate(new Date());
        bannerModelMapper.insertSelective(bannerModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新轮播图信息<>管理</>
     * @param banner
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Banner banner) {
        logger.debug("参数:{}", JsonUtils.deserializer(banner));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (banner.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        BannerModel criteria = new BannerModel();
        criteria.setTitle(banner.getTitle());
        List<BannerModel> list = bannerModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(banner.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        BannerModel bannerModel = bannerModelMapper.selectByPrimaryKey(banner.getId());
        if (bannerModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(banner, bannerModel);
        bannerModel.setUpdateBy(Context.get().getUserId().toString());
        bannerModel.setUpdateDate(new Date());
        bannerModelMapper.updateByPrimaryKeySelective(bannerModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取轮播图信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Banner> get(@PathVariable Integer id) {
        Result<Banner> result = new Result<>();
        BannerModel criteria = new BannerModel();
        criteria.setId(id);
        List<BannerModel> list = bannerModelMapper.search(criteria);//TODO:优化
        Banner banner = null;
        if (list != null && !list.isEmpty()) {
            banner = new Banner();
            BeanUtils.copyProperties(list.get(0), banner);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(banner);
        return result;
    }

    /**
     * 获取全部轮播图<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Banner>> list() {
        Result<List<Banner>> result = new Result<>();
        BannerModel criteria = new BannerModel();
        List<BannerModel> list = bannerModelMapper.search(criteria);//TODO:优化
        List<Banner> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(bannerModel -> {
                Banner banner = new Banner();
                BeanUtils.copyProperties(bannerModel, banner);
                list1.add(banner);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页轮播图<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Banner>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Banner>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        BannerModel criteria = new BannerModel();
        Page<BannerModel> page = (Page<BannerModel>)bannerModelMapper.search(criteria);//TODO:优化
        List<Banner> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(bannerModel -> {
                Banner banner = new Banner();
                BeanUtils.copyProperties(bannerModel, banner);
                list.add(banner);
            });
        }
        Pagination<Banner> pagination = new Pagination<>();
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
