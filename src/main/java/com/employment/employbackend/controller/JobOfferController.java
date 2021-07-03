package com.employment.employbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employment.employbackend.model.JobOffer;
import com.employment.employbackend.service.JobOfferService;

@RestController
@RequestMapping("/job-offer")
public class JobOfferController {

	@Autowired
	private JobOfferService jobOfferService;

	@GetMapping("/list")
	public ResponseEntity<List<JobOffer>> findAll() {
		List<JobOffer> jobOfferList = jobOfferService.findAll();
		if (!jobOfferList.isEmpty())
			return new ResponseEntity<>(jobOfferList, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
