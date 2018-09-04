package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.PlanModelMapper;
import com.example.demo.mapper.StationModelMapper;
import com.example.demo.mapper.UserModelMapper;
import com.example.demo.mapper.VehicleModelMapper;
import com.example.demo.model.PlanModel;
import com.example.demo.model.StationModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.VehicleModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/plan")
public class PlanController {

    private static Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    PlanModelMapper planModelMapper;

    @Autowired
    UserModelMapper userModelMapper;

    @Autowired
    VehicleModelMapper vehicleModelMapper;

    @Autowired
    StationModelMapper stationModelMapper;

    /**
     * 添加时间计划信息<>当前登录用户</>
     * @param plan
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Plan plan) {
        logger.debug("参数:{}", JsonUtils.deserializer(plan));

        Result<Void> result = new Result<>();
        PlanModel criteria = new PlanModel();
        criteria.setUserId(Context.get().getUserId());
        List<PlanModel> list = planModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        PlanModel planModel = new PlanModel();
        BeanUtils.copyProperties(plan, planModel);
        planModel.setUserId(Context.get().getUserId());
        planModelMapper.insertSelective(planModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新时间计划信息<>当前登录用户</>
     * @param plan
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Plan plan) {
        logger.debug("参数:{}", JsonUtils.deserializer(plan));

        Result<Void> result = new Result<>();
        if (plan.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        PlanModel planModel = planModelMapper.selectByPrimaryKey(plan.getId());
        if (planModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        if (!planModel.getUserId().equals(Context.get().getUserId())) {
            result.setCode(ResponseStatusEnum.AUTH.getCode());
            result.setMsg(ResponseStatusEnum.AUTH.getMsg());
            return result;
        }
        BeanUtils.copyProperties(plan, planModel);
        planModelMapper.updateByPrimaryKeySelective(planModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取时间计划信息<>当前登录用户</>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<Plan> get() {
        Result<Plan> result = new Result<>();
        PlanModel criteria = new PlanModel();
        criteria.setUserId(Context.get().getUserId());
        List<PlanModel> list = planModelMapper.search(criteria);//TODO:优化
        Plan plan = null;
        if (list != null && !list.isEmpty()) {
            plan = new Plan();
            BeanUtils.copyProperties(list.get(0), plan);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(plan);
        return result;
    }

    /**
     * 获取时间计划信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Plan> get(@PathVariable Integer id) {
        Result<Plan> result = new Result<>();
        PlanModel criteria = new PlanModel();
        criteria.setId(id);
        List<PlanModel> list = planModelMapper.search(criteria);//TODO:优化
        Plan plan = null;
        if (list != null && !list.isEmpty()) {
            plan = new Plan();
            BeanUtils.copyProperties(list.get(0), plan);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(plan);
        return result;
    }

    /**
     * 获取分页时间计划<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/list/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Plan>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Plan>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        PlanModel criteria = new PlanModel();
        Page<PlanModel> page = (Page<PlanModel>)planModelMapper.search(criteria);//TODO:优化
        List<Plan> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(planModel -> {
                Plan plan = new Plan();
                BeanUtils.copyProperties(planModel, plan);
                list.add(plan);
            });
        }
        Pagination<Plan> pagination = new Pagination<>();
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

    /**
     * 获取分页时间计划<>用户</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/complexList/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<ComplexPlan>> complexList(@PathVariable Integer pageNum) {
        Result<Pagination<ComplexPlan>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        PlanModel criteria = new PlanModel();
        Page<PlanModel> page = (Page<PlanModel>)planModelMapper.search(criteria);//TODO:优化
        List<ComplexPlan> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            List<Integer> userIdList = new ArrayList<>();
            List<Integer> stationIdList = new ArrayList<>();
            page.getResult().stream().forEach(planModel -> {
                if (!userIdList.contains(planModel.getUserId())) {
                    userIdList.add(planModel.getUserId());
                }
                if (!stationIdList.contains(planModel.getStationStart())) {
                    stationIdList.add(planModel.getStationStart());
                }
                if (!stationIdList.contains(planModel.getStationEnd())) {
                    stationIdList.add(planModel.getStationEnd());
                }
            });

            List<UserModel> userModelList = userModelMapper.selectByIdList(userIdList);
            Map<Integer, User> userMap = new HashMap<>();
            if (userModelList != null && !userModelList.isEmpty()) {
                userModelList.stream().forEach(userModel -> {
                    User user = new User();
                    BeanUtils.copyProperties(userModel, user);
                    userMap.put(user.getId(), user);
                });
            }
            List<VehicleModel> vehicleModelList = vehicleModelMapper.selectByUserIdList(userIdList);
            Map<Integer, Vehicle> vehicleMap = new HashMap<>();
            if (vehicleModelList != null && !vehicleModelList.isEmpty()) {
                vehicleModelList.stream().forEach(vehicleModel -> {
                    Vehicle vehicle = new Vehicle();
                    BeanUtils.copyProperties(vehicleModel, vehicle);
                    vehicleMap.put(vehicle.getId(), vehicle);
                });
            }
            List<StationModel> stationModelList = stationModelMapper.selectByIdList(stationIdList);
            Map<Integer, Station> stationMap = new HashMap<>();
            if (stationModelList != null && !stationModelList.isEmpty()) {
                stationModelList.stream().forEach(stationModel -> {
                    Station station = new Station();
                    BeanUtils.copyProperties(stationModel, station);
                    stationMap.put(station.getId(), station);
                });
            }

            page.getResult().stream().forEach(planModel -> {
                ComplexPlan complexPlan = new ComplexPlan();
                complexPlan.setId(planModel.getId());
                complexPlan.setUser(userMap.get(planModel.getUserId()));
                complexPlan.setVehicle(vehicleMap.get(planModel.getUserId()));
                complexPlan.setStationStart(stationMap.get(planModel.getStationStart()));
                complexPlan.setStationEnd(stationMap.get(planModel.getStationEnd()));
                complexPlan.setTime(planModel.getTime());
                complexPlan.setRemark(planModel.getRemark());
                list.add(complexPlan);
            });
        }
        Pagination<ComplexPlan> pagination = new Pagination<>();
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
