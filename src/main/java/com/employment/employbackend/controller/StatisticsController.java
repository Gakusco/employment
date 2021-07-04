package com.employment.employbackend.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employment.employbackend.rs.QuantityJobOfferByBusiness;
import com.employment.employbackend.service.BusinessService;
import com.employment.employbackend.service.JobOfferService;
import com.employment.employbackend.service.PostulantService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	PostulantService postulantService;

	@Autowired
	JobOfferService jobOfferService;

	@Autowired
	BusinessService businessService;

	@GetMapping("/quantity/applicants")
	public ResponseEntity<HashMap<String, Integer>> quantityApplicants() {
		HashMap<String, Integer> quantityApplicants = new HashMap<>();
		quantityApplicants.put("quantityApplicants", postulantService.findAll().size());
		return new ResponseEntity<>(quantityApplicants, HttpStatus.OK);
	}

	@GetMapping("/quantity/postulant/{postulantId}")
	public ResponseEntity<HashMap<String, Integer>> quantityApplicantsByPostulantId(@PathVariable String postulantId) {
		HashMap<String, Integer> quantityApplicants = new HashMap<>();
		quantityApplicants.put("quantityApplicants",
				jobOfferService.findOffersByPostulantId(Integer.parseInt(postulantId)).size());
		return new ResponseEntity<>(quantityApplicants, HttpStatus.OK);
	}

	@GetMapping("/quantity/job-offer-by-business")
	public ResponseEntity<HashMap<String, List<QuantityJobOfferByBusiness>>> jobOfferByBusiness() {
		HashMap<String, List<QuantityJobOfferByBusiness>> response = new HashMap<>();
		List<QuantityJobOfferByBusiness> quantityByBusiness = businessService.getQuantityJobOfferByBusiness();
		response.put("quantityByBusiness", quantityByBusiness);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
