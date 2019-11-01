package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.ActivityModelMapper;
import com.example.demo.model.ActivityModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Activity;
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
@RequestMapping(value = "/activity")
public class ActivityController {
    private static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    ActivityModelMapper activityModelMapper;

    /**
     * 添加活动信息<>管理</>
     * @param activity
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Activity activity) {
        logger.debug("参数:{}", JsonUtils.deserializer(activity));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        ActivityModel criteria = new ActivityModel();
        criteria.setTitle(activity.getTitle());
        List<ActivityModel> list = activityModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        ActivityModel activityModel = new ActivityModel();
        BeanUtils.copyProperties(activity, activityModel);
        activityModel.setCreateBy(Context.get().getUserId().toString());
        activityModel.setCreateDate(new Date());
        activityModel.setUpdateBy(Context.get().getUserId().toString());
        activityModel.setUpdateDate(new Date());
        activityModelMapper.insertSelective(activityModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新活动信息<>管理</>
     * @param activity
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Activity activity) {
        logger.debug("参数:{}", JsonUtils.deserializer(activity));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (activity.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        ActivityModel criteria = new ActivityModel();
        criteria.setTitle(activity.getTitle());
        List<ActivityModel> list = activityModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(activity.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        ActivityModel activityModel = activityModelMapper.selectByPrimaryKey(activity.getId());
        if (activityModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(activity, activityModel);
        activityModel.setUpdateBy(Context.get().getUserId().toString());
        activityModel.setUpdateDate(new Date());
        activityModelMapper.updateByPrimaryKeySelective(activityModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取活动信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Activity> get(@PathVariable Integer id) {
        Result<Activity> result = new Result<>();
        ActivityModel criteria = new ActivityModel();
        criteria.setId(id);
        List<ActivityModel> list = activityModelMapper.search(criteria);//TODO:优化
        Activity activity = null;
        if (list != null && !list.isEmpty()) {
            activity = new Activity();
            BeanUtils.copyProperties(list.get(0), activity);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(activity);
        return result;
    }

    /**
     * 获取全部活动<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Activity>> list() {
        Result<List<Activity>> result = new Result<>();
        ActivityModel criteria = new ActivityModel();
        List<ActivityModel> list = activityModelMapper.search(criteria);//TODO:优化
        List<Activity> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(activityModel -> {
                Activity activity = new Activity();
                BeanUtils.copyProperties(activityModel, activity);
                list1.add(activity);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页活动<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Activity>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Activity>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        ActivityModel criteria = new ActivityModel();
        Page<ActivityModel> page = (Page<ActivityModel>)activityModelMapper.search(criteria);//TODO:优化
        List<Activity> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(activityModel -> {
                Activity activity = new Activity();
                BeanUtils.copyProperties(activityModel, activity);
                list.add(activity);
            });
        }
        Pagination<Activity> pagination = new Pagination<>();
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
