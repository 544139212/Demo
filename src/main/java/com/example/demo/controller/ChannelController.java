package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.ChannelModelMapper;
import com.example.demo.model.ChannelModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Channel;
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
@RequestMapping(value = "/channel")
public class ChannelController {
    private static Logger logger = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    ChannelModelMapper channelModelMapper;

    /**
     * 添加频道信息<>管理</>
     * @param channel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Channel channel) {
        logger.debug("参数:{}", JsonUtils.deserializer(channel));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        ChannelModel criteria = new ChannelModel();
        criteria.setName(channel.getName());
        List<ChannelModel> list = channelModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        ChannelModel channelModel = new ChannelModel();
        BeanUtils.copyProperties(channel, channelModel);
        channelModel.setCreateBy(Context.get().getUserId().toString());
        channelModel.setCreateDate(new Date());
        channelModel.setUpdateBy(Context.get().getUserId().toString());
        channelModel.setUpdateDate(new Date());
        channelModelMapper.insertSelective(channelModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新频道信息<>管理</>
     * @param channel
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Channel channel) {
        logger.debug("参数:{}", JsonUtils.deserializer(channel));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (channel.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        ChannelModel criteria = new ChannelModel();
        criteria.setName(channel.getName());
        List<ChannelModel> list = channelModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(channel.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        ChannelModel channelModel = channelModelMapper.selectByPrimaryKey(channel.getId());
        if (channelModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(channel, channelModel);
        channelModel.setUpdateBy(Context.get().getUserId().toString());
        channelModel.setUpdateDate(new Date());
        channelModelMapper.updateByPrimaryKeySelective(channelModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取频道信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Channel> get(@PathVariable Integer id) {
        Result<Channel> result = new Result<>();
        ChannelModel criteria = new ChannelModel();
        criteria.setId(id);
        List<ChannelModel> list = channelModelMapper.search(criteria);//TODO:优化
        Channel channel = null;
        if (list != null && !list.isEmpty()) {
            channel = new Channel();
            BeanUtils.copyProperties(list.get(0), channel);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(channel);
        return result;
    }

    /**
     * 获取全部频道<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Channel>> list() {
        Result<List<Channel>> result = new Result<>();
        ChannelModel criteria = new ChannelModel();
        List<ChannelModel> list = channelModelMapper.search(criteria);//TODO:优化
        List<Channel> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(channelModel -> {
                Channel channel = new Channel();
                BeanUtils.copyProperties(channelModel, channel);
                list1.add(channel);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页频道<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Channel>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Channel>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        ChannelModel criteria = new ChannelModel();
        Page<ChannelModel> page = (Page<ChannelModel>)channelModelMapper.search(criteria);//TODO:优化
        List<Channel> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(channelModel -> {
                Channel channel = new Channel();
                BeanUtils.copyProperties(channelModel, channel);
                list.add(channel);
            });
        }
        Pagination<Channel> pagination = new Pagination<>();
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
