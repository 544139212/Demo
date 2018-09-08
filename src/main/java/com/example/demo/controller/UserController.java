package com.example.demo.controller;

import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.context.Context;
import com.example.demo.mapper.UserModelMapper;
import com.example.demo.model.UserModel;
import com.example.demo.vo.Result;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserModelMapper userModelMapper;

	/**
	 * 更新用户信息<>当前登录用户</>
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Result<Void> update(@RequestBody @Valid User user) {
		logger.debug("参数:{}", JsonUtils.deserializer(user));

		Result<Void> result = new Result<>();

		// 方式一
		/*if (user.getId() == null) {
			result.setCode(ResponseStatusEnum.INVALID.getCode());
			result.setMsg(ResponseStatusEnum.INVALID.getMsg());
			return result;
		}
		UserModel userModel = userModelMapper.selectByPrimaryKey(user.getId());
		if (userModel == null) {
			result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
			result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
			return result;
		}
		if (!userModel.getId().equals(Context.get().getUserId())) {
			result.setCode(ResponseStatusEnum.AUTH.getCode());
			result.setMsg(ResponseStatusEnum.AUTH.getMsg());
			return result;
		}
		BeanUtils.copyProperties(user, userModel);
		userModelMapper.updateByPrimaryKeySelective(userModel);*/

		// 方式二
		UserModel userModel = userModelMapper.selectByPrimaryKey(Context.get().getUserId());
		if (userModel == null) {
			result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
			result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
			return result;
		}
		BeanUtils.copyProperties(user, userModel);
		userModel.setId(Context.get().getUserId());
		userModel.setUpdateBy(Context.get().getUserId().toString());
		userModel.setUpdateDate(new Date());
		userModelMapper.updateByPrimaryKeySelective(userModel);

		result.setCode(ResponseStatusEnum.SUCCESS.getCode());
		result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
		return result;
	}

	/**
	 * 获取用户信息<>当前登录用户</>
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result<User> get() {
		Result<User> result = new Result<>();
		UserModel userModel = userModelMapper.selectByPrimaryKey(Context.get().getUserId());
		User user = null;
		if (userModel != null) {
			user = new User();
			BeanUtils.copyProperties(userModel, user);
		}
		result.setCode(ResponseStatusEnum.SUCCESS.getCode());
		result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
		result.setData(user);
		return result;
	}

	/**
	 * 获取用户信息<>用户/管理</>
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result<User> get(@PathVariable Integer id) {
		Result<User> result = new Result<>();
		UserModel userModel = userModelMapper.selectByPrimaryKey(id);
		User user = null;
		if (userModel != null) {
			user = new User();
			BeanUtils.copyProperties(userModel, user);
		}
		result.setCode(ResponseStatusEnum.SUCCESS.getCode());
		result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
		result.setData(user);
		return result;
	}

	/**
	 * 获取分页用户<>管理</>
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
	public Result<Pagination<User>> list(@PathVariable Integer pageNum) {
		Result<Pagination<User>> result = new Result<>();
		//TODO: 鉴权
		PageHelper.startPage(pageNum, 20);
		UserModel criteria = new UserModel();
		Page<UserModel> page = (Page<UserModel>)userModelMapper.search(criteria);//TODO:优化
		List<User> list = new ArrayList<>();
		if (page.getResult() != null && !page.getResult().isEmpty()) {
			page.getResult().stream().forEach(userModel -> {
				User user = new User();
				BeanUtils.copyProperties(userModel, user);
				list.add(user);
			});
		}
		Pagination<User> pagination = new Pagination<>();
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
