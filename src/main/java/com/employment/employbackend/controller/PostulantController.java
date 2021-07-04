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

import com.employment.employbackend.model.JobOffer;
import com.employment.employbackend.model.Postulant;
import com.employment.employbackend.service.JobOfferService;
import com.employment.employbackend.service.PostulantService;

@RestController
@RequestMapping("/postulant")
public class PostulantController {

	@Autowired
	private PostulantService postulantService;

	@Autowired
	private JobOfferService jobOfferService;

	@GetMapping("/list")
	public ResponseEntity<HashMap<String, List<Postulant>>> findAll() {
		HashMap<String, List<Postulant>> response = new HashMap<>();
		List<Postulant> userList = postulantService.findAll();
		if (!userList.isEmpty()) {
			response.put("applicants", userList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/applications/{postulantId}")
	public ResponseEntity<HashMap<String, List<JobOffer>>> findAllApplications(@PathVariable String postulantId) {
		HashMap<String, List<JobOffer>> response = new HashMap<>();
		List<JobOffer> jobOfferList = jobOfferService.findOffersByPostulantId(Integer.parseInt(postulantId));
		response.put("postulantList", jobOfferList);
		if (!jobOfferList.isEmpty())
			return new ResponseEntity<>(response, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
