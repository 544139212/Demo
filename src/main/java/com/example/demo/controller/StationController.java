package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.StationModelMapper;
import com.example.demo.model.StationModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.example.demo.vo.Station;
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
@RequestMapping(value = "/station")
public class StationController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    StationModelMapper stationModelMapper;

    /**
     * 添加站点信息<>管理</>
     * @param station
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Station station) {
        logger.debug("参数:{}", JsonUtils.deserializer(station));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        StationModel criteria = new StationModel();
        criteria.setName(station.getName());
        List<StationModel> list = stationModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        StationModel stationModel = new StationModel();
        BeanUtils.copyProperties(station, stationModel);
        stationModel.setCreateBy(Context.get().getUserId().toString());
        stationModel.setCreateDate(new Date());
        stationModel.setUpdateBy(Context.get().getUserId().toString());
        stationModel.setUpdateDate(new Date());
        stationModelMapper.insertSelective(stationModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新站点信息<>管理</>
     * @param station
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Station station) {
        logger.debug("参数:{}", JsonUtils.deserializer(station));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (station.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        StationModel criteria = new StationModel();
        criteria.setName(station.getName());
        List<StationModel> list = stationModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(station.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        StationModel stationModel = stationModelMapper.selectByPrimaryKey(station.getId());
        if (stationModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(station, stationModel);
        stationModel.setUpdateBy(Context.get().getUserId().toString());
        stationModel.setUpdateDate(new Date());
        stationModelMapper.updateByPrimaryKeySelective(stationModel);
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
    public Result<Station> get(@PathVariable Integer id) {
        Result<Station> result = new Result<>();
        StationModel criteria = new StationModel();
        criteria.setId(id);
        List<StationModel> list = stationModelMapper.search(criteria);//TODO:优化
        Station station = null;
        if (list != null && !list.isEmpty()) {
            station = new Station();
            BeanUtils.copyProperties(list.get(0), station);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(station);
        return result;
    }

    /**
     * 获取全部站点<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Station>> list() {
        Result<List<Station>> result = new Result<>();
        StationModel criteria = new StationModel();
        List<StationModel> list = stationModelMapper.search(criteria);//TODO:优化
        List<Station> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(stationModel -> {
                Station station = new Station();
                BeanUtils.copyProperties(stationModel, station);
                list1.add(station);
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
    public Result<Pagination<Station>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Station>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        StationModel criteria = new StationModel();
        Page<StationModel> page = (Page<StationModel>)stationModelMapper.search(criteria);//TODO:优化
        List<Station> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(stationModel -> {
                Station station = new Station();
                BeanUtils.copyProperties(stationModel, station);
                list.add(station);
            });
        }
        Pagination<Station> pagination = new Pagination<>();
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
