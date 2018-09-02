package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.DriverLicenseModelMapper;
import com.example.demo.model.DriverLicenseModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.DriverLicense;
import com.example.demo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/driverLicense")
public class DriverLicenseController {

    private static Logger logger = LoggerFactory.getLogger(DriverLicenseController.class);

    @Autowired
    DriverLicenseModelMapper driverLicenseModelMapper;

    /**
     * 添加驾驶执照信息<>当前登录用户</>
     * @param driverLicense
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid DriverLicense driverLicense) {
        logger.debug("参数:{}", JsonUtils.deserializer(driverLicense));

        Result<Void> result = new Result<>();
        DriverLicenseModel criteria = new DriverLicenseModel();
        criteria.setUserId(Context.get().getUserId());
        List<DriverLicenseModel> list = driverLicenseModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        DriverLicenseModel driverLicenseModel = new DriverLicenseModel();
        driverLicenseModel.setUserId(Context.get().getUserId());
        BeanUtils.copyProperties(driverLicense, driverLicenseModel);
        driverLicenseModelMapper.insertSelective(driverLicenseModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新驾驶执照信息<>当前登录用户</>
     * @param driverLicense
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid DriverLicense driverLicense) {
        logger.debug("参数:{}", JsonUtils.deserializer(driverLicense));

        Result<Void> result = new Result<>();
        DriverLicenseModel driverLicenseModel = driverLicenseModelMapper.selectByPrimaryKey(driverLicense.getId());
        if (driverLicenseModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        if (!driverLicenseModel.getUserId().equals(Context.get().getUserId())) {
            result.setCode(ResponseStatusEnum.AUTH.getCode());
            result.setMsg(ResponseStatusEnum.AUTH.getMsg());
            return result;
        }
        BeanUtils.copyProperties(driverLicense, driverLicenseModel);
        driverLicenseModelMapper.updateByPrimaryKeySelective(driverLicenseModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取驾驶执照信息<>当前登录用户</>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<DriverLicense> get() {
        Result<DriverLicense> result = new Result<>();
        DriverLicenseModel criteria = new DriverLicenseModel();
        criteria.setUserId(Context.get().getUserId());
        List<DriverLicenseModel> list = driverLicenseModelMapper.search(criteria);//TODO:优化
        DriverLicense driverLicense = null;
        if (list != null && !list.isEmpty()) {
            driverLicense = new DriverLicense();
            BeanUtils.copyProperties(list.get(0), driverLicense);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(driverLicense);
        return result;
    }

    /**
     * 获取驾驶执照信息<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<DriverLicense> get(@PathVariable Integer id) {
        Result<DriverLicense> result = new Result<>();
        DriverLicenseModel criteria = new DriverLicenseModel();
        criteria.setId(id);
        List<DriverLicenseModel> list = driverLicenseModelMapper.search(criteria);//TODO:优化
        DriverLicense driverLicense = null;
        if (list != null && !list.isEmpty()) {
            driverLicense = new DriverLicense();
            BeanUtils.copyProperties(list.get(0), driverLicense);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(driverLicense);
        return result;
    }
}
