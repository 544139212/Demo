package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.PlanModelMapper;
import com.example.demo.model.PlanModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Plan;
import com.example.demo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/plan")
public class PlanController {

    private static Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    PlanModelMapper planModelMapper;

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
}
