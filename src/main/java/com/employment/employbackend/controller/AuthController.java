package com.employment.employbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employment.employbackend.service.PostulantService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PostulantService postulantService;

	@PostMapping("/register")
	private ResponseEntity<String> register() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
