package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.LocationModelMapper;
import com.example.demo.model.LocationModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Location;
import com.example.demo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/location")
public class LocationController {

    private static Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    LocationModelMapper locationModelMapper;

    /**
     * 添加位置信息<>当前登录用户</>
     * @param location
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Location location) {
        logger.debug("参数:{}", JsonUtils.deserializer(location));

        Result<Void> result = new Result<>();
        LocationModel locationModel = new LocationModel();
        locationModel.setUserId(Context.get().getUserId());
        BeanUtils.copyProperties(location, locationModel);
        locationModelMapper.insertSelective(locationModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取最近一次位置信息<>当前登录用户</>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<Location> getLocation() {
        Result<Location> result = new Result<>();
        LocationModel locationModel = locationModelMapper.getLocation(Context.get().getUserId());
        Location location = null;
        if (locationModel != null) {
            location = new Location();
            BeanUtils.copyProperties(locationModel, location);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(location);
        return result;
    }

}
