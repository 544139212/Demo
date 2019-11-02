package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.BroadcastModelMapper;
import com.example.demo.model.BroadcastModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Broadcast;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
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
@RequestMapping(value = "/broadcast")
public class BroadcastController {
    private static Logger logger = LoggerFactory.getLogger(BroadcastController.class);

    @Autowired
    BroadcastModelMapper broadcastModelMapper;

    /**
     * 添加广播信息<>管理</>
     * @param broadcast
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Broadcast broadcast) {
        logger.debug("参数:{}", JsonUtils.deserializer(broadcast));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        BroadcastModel criteria = new BroadcastModel();
        criteria.setTitle(broadcast.getTitle());
        List<BroadcastModel> list = broadcastModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        BroadcastModel broadcastModel = new BroadcastModel();
        BeanUtils.copyProperties(broadcast, broadcastModel);
        broadcastModel.setCreateBy(Context.get().getUserId().toString());
        broadcastModel.setCreateDate(new Date());
        broadcastModel.setUpdateBy(Context.get().getUserId().toString());
        broadcastModel.setUpdateDate(new Date());
        broadcastModelMapper.insertSelective(broadcastModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新广播信息<>管理</>
     * @param broadcast
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Broadcast broadcast) {
        logger.debug("参数:{}", JsonUtils.deserializer(broadcast));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (broadcast.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        BroadcastModel criteria = new BroadcastModel();
        criteria.setTitle(broadcast.getTitle());
        List<BroadcastModel> list = broadcastModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(broadcast.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        BroadcastModel broadcastModel = broadcastModelMapper.selectByPrimaryKey(broadcast.getId());
        if (broadcastModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(broadcast, broadcastModel);
        broadcastModel.setUpdateBy(Context.get().getUserId().toString());
        broadcastModel.setUpdateDate(new Date());
        broadcastModelMapper.updateByPrimaryKeySelective(broadcastModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取广播信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Broadcast> get(@PathVariable Integer id) {
        Result<Broadcast> result = new Result<>();
        BroadcastModel criteria = new BroadcastModel();
        criteria.setId(id);
        List<BroadcastModel> list = broadcastModelMapper.search(criteria);//TODO:优化
        Broadcast broadcast = null;
        if (list != null && !list.isEmpty()) {
            broadcast = new Broadcast();
            BeanUtils.copyProperties(list.get(0), broadcast);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(broadcast);
        return result;
    }

    /**
     * 获取全部广播<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Broadcast>> list() {
        Result<List<Broadcast>> result = new Result<>();
        BroadcastModel criteria = new BroadcastModel();
        List<BroadcastModel> list = broadcastModelMapper.search(criteria);//TODO:优化
        List<Broadcast> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(broadcastModel -> {
                Broadcast broadcast = new Broadcast();
                BeanUtils.copyProperties(broadcastModel, broadcast);
                list1.add(broadcast);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页广播<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Broadcast>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Broadcast>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        BroadcastModel criteria = new BroadcastModel();
        Page<BroadcastModel> page = (Page<BroadcastModel>)broadcastModelMapper.search(criteria);//TODO:优化
        List<Broadcast> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(broadcastModel -> {
                Broadcast broadcast = new Broadcast();
                BeanUtils.copyProperties(broadcastModel, broadcast);
                list.add(broadcast);
            });
        }
        Pagination<Broadcast> pagination = new Pagination<>();
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
