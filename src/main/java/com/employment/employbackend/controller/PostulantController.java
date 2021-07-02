package com.employment.employbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employment.employbackend.model.Postulant;
import com.employment.employbackend.service.PostulantService;

@RestController
@RequestMapping("/postulant")
public class PostulantController {

	@Autowired
	private PostulantService postulantService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Postulant>> findAll() {
		List<Postulant> userList = postulantService.findAll();
		if (!userList.isEmpty()) return new ResponseEntity<>(userList, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
