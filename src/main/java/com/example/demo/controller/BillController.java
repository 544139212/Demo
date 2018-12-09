package com.example.demo.controller;

import com.example.demo.mapper.BillModelMapper;
import com.example.demo.dto.DateBillListRespWsDTO;
import com.example.demo.dto.DateBillWsDTO;
import com.example.demo.dto.BillWsDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.model.BillModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping(value = "/bill")
public class BillController {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	BillModelMapper billModelMapper;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultDTO add(@RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "cost", required = true) String cost) {
		try {
			BillModel billModel = new BillModel();
			billModel.setName(name);
			billModel.setCost(cost);

			billModelMapper.insertSelective(billModel);

			return new ResultDTO(200, "success", null);
		} catch (Exception e) {
		    e.printStackTrace();
			return new ResultDTO(500, e.getMessage(), null);
		}
	}

	@RequestMapping(value = "/getBills", method = RequestMethod.GET)
	public DateBillListRespWsDTO getBills() {
		try {
			List<BillModel> billModels = billModelMapper.findAll();
			List<DateBillWsDTO> dateBillWsDTOS = new ArrayList<>();

			if (billModels != null && !billModels.isEmpty()) {
				Map<Date, List<BillWsDTO>> map = new LinkedHashMap<>();
				ModelMapper modelMapper = new ModelMapper();
				for (BillModel billModel : billModels) {
					if (map.containsKey(billModel.getDate())) {
						map.get(billModel.getDate()).add(modelMapper.map(billModel, BillWsDTO.class));
					} else {
						List<BillWsDTO> list = new ArrayList<>();
						list.add(modelMapper.map(billModel, BillWsDTO.class));
						map.put(billModel.getDate(), list);
					}
				}

				DateBillWsDTO dateBillWsDTO;
				for (Map.Entry<Date, List<BillWsDTO>> entry : map.entrySet()) {
					dateBillWsDTO = new DateBillWsDTO();
					dateBillWsDTO.setDate(format.format(entry.getKey()));
					dateBillWsDTO.setList(entry.getValue());
					dateBillWsDTOS.add(dateBillWsDTO);
				}
			}

			return new DateBillListRespWsDTO(200, "success", dateBillWsDTOS);
		} catch (Exception e) {
		    e.printStackTrace();
			return new DateBillListRespWsDTO(500, e.getMessage(), null);
		}
	}

}
