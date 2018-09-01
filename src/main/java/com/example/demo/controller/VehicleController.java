package com.example.demo.controller;

import com.example.demo.vo.Vehicle;
import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.VehicleModelMapper;
import com.example.demo.model.VehicleModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    VehicleModelMapper vehicleModelMapper;

    /**
     * 添加车辆信息<>当前登录用户</>
     * @param vehicle
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Vehicle vehicle) {
        logger.debug("参数:{}", JsonUtils.deserializer(vehicle));

        Result<Void> result = new Result<>();
        VehicleModel criteria = new VehicleModel();
        criteria.setUserId(Context.get().getUserId());
        List<VehicleModel> list = vehicleModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setUserId(Context.get().getUserId());
        BeanUtils.copyProperties(vehicle, vehicleModel);
        vehicleModelMapper.insertSelective(vehicleModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新车辆信息<>当前登录用户</>
     * @param vehicle
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Vehicle vehicle) {
        logger.debug("参数:{}", JsonUtils.deserializer(vehicle));

        Result<Void> result = new Result<>();
        VehicleModel vehicleModel = vehicleModelMapper.selectByPrimaryKey(vehicle.getId());
        if (vehicleModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        if (!vehicleModel.getUserId().equals(Context.get().getUserId())) {
            result.setCode(ResponseStatusEnum.AUTH.getCode());
            result.setMsg(ResponseStatusEnum.AUTH.getMsg());
            return result;
        }
        BeanUtils.copyProperties(vehicle, vehicleModel);
        vehicleModelMapper.updateByPrimaryKeySelective(vehicleModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取车辆信息<>当前登录用户</>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<Vehicle> getLocation() {
        Result<Vehicle> result = new Result<>();
        VehicleModel criteria = new VehicleModel();
        criteria.setUserId(Context.get().getUserId());
        List<VehicleModel> list = vehicleModelMapper.search(criteria);//TODO:优化
        Vehicle vehicle = null;
        if (list != null && !list.isEmpty()) {
            vehicle = new Vehicle();
            BeanUtils.copyProperties(list.get(0), vehicle);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(vehicle);
        return result;
    }

    /**
     * 获取车辆信息<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Vehicle> getLocation(@PathVariable Integer id) {
        Result<Vehicle> result = new Result<>();
        VehicleModel criteria = new VehicleModel();
        criteria.setId(id);
        List<VehicleModel> list = vehicleModelMapper.search(criteria);//TODO:优化
        Vehicle vehicle = null;
        if (list != null && !list.isEmpty()) {
            vehicle = new Vehicle();
            BeanUtils.copyProperties(list.get(0), vehicle);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(vehicle);
        return result;
    }

}
