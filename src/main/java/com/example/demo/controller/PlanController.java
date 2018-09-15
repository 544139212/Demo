package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.AppointTypeEnum;
import com.example.demo.enums.PlanTypeEnum;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import com.example.demo.util.DateUtils;
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
import java.util.*;

@RestController
@RequestMapping(value = "/plan")
public class PlanController {

    private static Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    PlanModelMapper planModelMapper;

    @Autowired
    UserModelMapper userModelMapper;

    @Autowired
    LocationModelMapper locationModelMapper;

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
        // 当类型为"车找人"时，需校验车辆信息是否存在
        if (PlanTypeEnum.CHE_ZHAO_REN.getType() == plan.getPlanType().byteValue()) {
            VehicleModel criteria = new VehicleModel();
            criteria.setUserId(Context.get().getUserId());
            List<VehicleModel> list = vehicleModelMapper.search(criteria);//TODO:优化
            if (list == null || list.isEmpty()) {
                result.setCode(ResponseStatusEnum.VEHICLE_NOT_FOUND.getCode());
                result.setMsg(ResponseStatusEnum.VEHICLE_NOT_FOUND.getMsg());
                return result;
            }
        }

        // 校验出发地站点是否存在
        StationModel criteria = new StationModel();
        criteria.setId(plan.getStationStart());
        List<StationModel> list = stationModelMapper.search(criteria);//TODO:优化
        if (list == null || list.isEmpty()) {
            result.setCode(ResponseStatusEnum.STATION_START_INVALID.getCode());
            result.setMsg(ResponseStatusEnum.STATION_START_INVALID.getMsg());
            return result;
        }

        // 校验目的地站点是否存在
        criteria.setId(plan.getStationEnd());
        list = stationModelMapper.search(criteria);//TODO:优化
        if (list == null || list.isEmpty()) {
            result.setCode(ResponseStatusEnum.STATION_END_INVALID.getCode());
            result.setMsg(ResponseStatusEnum.STATION_END_INVALID.getMsg());
            return result;
        }

        if (plan.getStationStart().equals(plan.getStationEnd())) {
            result.setCode(ResponseStatusEnum.STATION_START_END_EQUAL_ERROR.getCode());
            result.setMsg(ResponseStatusEnum.STATION_START_END_EQUAL_ERROR.getMsg());
            return result;
        }

        // 校验出发时间
        if (AppointTypeEnum.NOW.getType() == plan.getAppointType().byteValue()) {
            plan.setDate(DateUtils.toDateString(new Date()));
            plan.setTime(DateUtils.toShortTimeString(new Date()));
        } else if (AppointTypeEnum.OTHER.getType() == plan.getAppointType().byteValue()) {
            // 校验日期
            Date planDate = DateUtils.toDate(plan.getDate(), DateUtils.DEFAULT_PATTERN_DATE);
            Date nowDate = DateUtils.toDate(DateUtils.toDateString(new Date()), DateUtils.DEFAULT_PATTERN_DATE);
            if (planDate.before(nowDate)) {
                result.setCode(ResponseStatusEnum.DATE_INVALID.getCode());
                result.setMsg(ResponseStatusEnum.DATE_INVALID.getMsg());
                return result;
            }

            // 校验时间
            Date planDateTime = DateUtils.toDate(plan.getDate() + " " + plan.getTime(), DateUtils.DEFAULT_PATTERN_DATETIME_SHORT);
            Date nowDateTime = DateUtils.toDate(DateUtils.toString(new Date()), DateUtils.DEFAULT_PATTERN_DATETIME_SHORT);
            if (planDateTime.before(nowDateTime)) {
                result.setCode(ResponseStatusEnum.TIME_INVALID.getCode());
                result.setMsg(ResponseStatusEnum.TIME_INVALID.getMsg());
                return result;
            }
        }

        // 校验出行人数|剩余空位
        if (plan.getNum() == null || plan.getNum() < 1) {
            if (PlanTypeEnum.REN_ZHAO_CHE.getType() == plan.getPlanType().byteValue()) {
                result.setCode(ResponseStatusEnum.REN_ZHAO_CHE_NUM_INVALID.getCode());
                result.setMsg(ResponseStatusEnum.REN_ZHAO_CHE_NUM_INVALID.getMsg());
            } else if (PlanTypeEnum.CHE_ZHAO_REN.getType() == plan.getPlanType().byteValue()) {
                result.setCode(ResponseStatusEnum.CHE_ZHAO_REN_NUM_INVALID.getCode());
                result.setMsg(ResponseStatusEnum.CHE_ZHAO_REN_NUM_INVALID.getMsg());
            }
            return result;
        }

        PlanModel planModel = new PlanModel();
        BeanUtils.copyProperties(plan, planModel);
        planModel.setUserId(Context.get().getUserId());
        planModel.setCreateBy(Context.get().getUserId().toString());
        planModel.setCreateDate(new Date());
        planModel.setUpdateBy(Context.get().getUserId().toString());
        planModel.setUpdateDate(new Date());
        planModelMapper.insertSelective(planModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
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
            List<LocationModel> locationModelList = locationModelMapper.selectByUserIdList(userIdList);
            Map<Integer, Location> locationMap = new HashMap<>();
            if (locationModelList != null && !locationModelList.isEmpty()) {
                locationModelList.stream().forEach(locationModel -> {
                    Location location = new Location();
                    BeanUtils.copyProperties(locationModel, location);
                    locationMap.put(location.getUserId(), location);
                });
            }
            List<VehicleModel> vehicleModelList = vehicleModelMapper.selectByUserIdList(userIdList);
            Map<Integer, Vehicle> vehicleMap = new HashMap<>();
            if (vehicleModelList != null && !vehicleModelList.isEmpty()) {
                vehicleModelList.stream().forEach(vehicleModel -> {
                    Vehicle vehicle = new Vehicle();
                    BeanUtils.copyProperties(vehicleModel, vehicle);
                    vehicleMap.put(vehicle.getUserId(), vehicle);
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
                complexPlan.setLocation(locationMap.get(planModel.getUserId()));
                complexPlan.setVehicle(vehicleMap.get(planModel.getUserId()));
                complexPlan.setStationStart(stationMap.get(planModel.getStationStart()));
                complexPlan.setStationEnd(stationMap.get(planModel.getStationEnd()));
                complexPlan.setDate(planModel.getDate());
                complexPlan.setTime(planModel.getTime());
                complexPlan.setNum(planModel.getNum());
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
