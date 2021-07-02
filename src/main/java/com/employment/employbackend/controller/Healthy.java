package com.employment.employbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Healthy {
	
	@GetMapping({"", "/"})
	public ResponseEntity<String> getStatusOk() {
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
