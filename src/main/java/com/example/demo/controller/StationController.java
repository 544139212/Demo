package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.StationModelMapper;
import com.example.demo.model.StationModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Result;
import com.example.demo.vo.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        stationModelMapper.updateByPrimaryKeySelective(stationModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取站点信息<>用户/管理</>
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
}
