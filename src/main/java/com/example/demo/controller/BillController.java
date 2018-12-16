package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.BillModelMapper;
import com.example.demo.model.BillModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Bill;
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
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping(value = "/bill")
public class BillController {

	private static Logger logger = LoggerFactory.getLogger(BillController.class);

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	BillModelMapper billModelMapper;

	/**
	 * 添加账单信息<>当前登录用户</>
	 * @param bill
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result<Void> add(@RequestBody @Valid Bill bill) {
		logger.debug("参数:{}", JsonUtils.deserializer(bill));

		Result<Void> result = new Result<>();
		BillModel billModel = new BillModel();
		BeanUtils.copyProperties(bill, billModel);
		billModel.setUserId(Context.get().getUserId());
		billModel.setCreateBy(Context.get().getUserId().toString());
		billModel.setCreateDate(new Date());
		billModel.setUpdateBy(Context.get().getUserId().toString());
		billModel.setUpdateDate(new Date());
		billModelMapper.insertSelective(billModel);
		result.setCode(ResponseStatusEnum.SUCCESS.getCode());
		result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
		return result;
	}

	/**
	 * 获取分页账单<>当前登录用户</>
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
	public Result<Pagination<Bill>> list(@PathVariable Integer pageNum) {
		Result<Pagination<Bill>> result = new Result<>();
		PageHelper.startPage(pageNum, 20);
		BillModel criteria = new BillModel();
		criteria.setUserId(Context.get().getUserId());
		Page<BillModel> page = (Page<BillModel>)billModelMapper.search(criteria);//TODO:优化
		List<Bill> list = new ArrayList<>();
		if (page.getResult() != null && !page.getResult().isEmpty()) {
			page.getResult().stream().forEach(billModel -> {
				Bill bill = new Bill();
				BeanUtils.copyProperties(billModel, bill);
				list.add(bill);
			});
		}
		Pagination<Bill> pagination = new Pagination<>();
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
