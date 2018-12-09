package com.example.demo.controller;

import com.example.demo.mapper.FoodModelMapper;
import com.example.demo.model.FoodModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping(value = "/food")
public class FoodController {

	@Autowired
	FoodModelMapper foodModelMapper;

	@RequestMapping(value = "/getFoods", method = RequestMethod.GET)
	public List<FoodModel> getFoods() {
		try {
			List<FoodModel> billModels = foodModelMapper.findAll();
			return billModels;
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}

}
