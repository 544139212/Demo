package com.example.demo.controller;

import com.example.demo.vo.Pagination;
import com.example.demo.vo.Vehicle;
import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.VehicleModelMapper;
import com.example.demo.model.VehicleModel;
import com.example.demo.util.JsonUtils;
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
        BeanUtils.copyProperties(vehicle, vehicleModel);
        vehicleModel.setUserId(Context.get().getUserId());
        vehicleModel.setCreateBy(Context.get().getUserId().toString());
        vehicleModel.setCreateDate(new Date());
        vehicleModel.setUpdateBy(Context.get().getUserId().toString());
        vehicleModel.setUpdateDate(new Date());
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
        if (vehicle.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
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
        vehicleModel.setUpdateBy(Context.get().getUserId().toString());
        vehicleModel.setUpdateDate(new Date());
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
    public Result<Vehicle> get() {
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Vehicle> get(@PathVariable Integer id) {
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

    /**
     * 获取分页车辆<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Vehicle>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Vehicle>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        VehicleModel criteria = new VehicleModel();
        Page<VehicleModel> page = (Page<VehicleModel>)vehicleModelMapper.search(criteria);//TODO:优化
        List<Vehicle> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(vehicleModel -> {
                Vehicle vehicle = new Vehicle();
                BeanUtils.copyProperties(vehicleModel, vehicle);
                list.add(vehicle);
            });
        }
        Pagination<Vehicle> pagination = new Pagination<>();
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
