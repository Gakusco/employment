package com.employment.employbackend.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employment.employbackend.exception.EmploymentException;
import com.employment.employbackend.helper.ResponseValidate;
import com.employment.employbackend.model.Business;
import com.employment.employbackend.service.BusinessService;

@RestController
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	private BusinessService businessService;

	@GetMapping("/list")
	public ResponseEntity<HashMap<String, List<Business>>> findAll() {
		HashMap<String, List<Business>> response = new HashMap<>();
		List<Business> business = businessService.findAll();
		if (!business.isEmpty()) {
			response.put("businessList", business);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/add")
	public ResponseEntity<?> create(@Valid @RequestBody Business business, BindingResult result) {
		HashMap<String, List<String>> response = new HashMap<>();
		if (result.hasErrors()) {
			response.put(ResponseValidate.ERROR, ResponseValidate.resultErrorsToList(result));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			return new ResponseEntity<>(businessService.save(business), HttpStatus.CREATED);
		} catch (EmploymentException error) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
