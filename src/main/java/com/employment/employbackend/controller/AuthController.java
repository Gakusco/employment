package com.employment.employbackend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employment.employbackend.exception.EmploymentException;
import com.employment.employbackend.helper.Constants;
import com.employment.employbackend.helper.ResponseValidate;
import com.employment.employbackend.model.Postulant;
import com.employment.employbackend.model.Role;
import com.employment.employbackend.service.PostulantService;
import com.employment.employbackend.service.RoleService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PostulantService postulantService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody Postulant postulant, BindingResult result) {
		HashMap<String, List<String>> response = new HashMap<>();
		if (result.hasErrors()) {
			response.put(ResponseValidate.ERROR, ResponseValidate.resultErrorsToList(result));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		List<Role> roles = new ArrayList<>();
		postulant.getCredential().setRoles(roles);
		try {
			roleService.findById(Constants.ROLE_POSTULANT)
					.ifPresent(role -> postulant.getCredential().getRoles().add(role));
			postulant.getCredential().setEnabled(true);
			postulant.getCredential().setPassword(passwordEncoder.encode(postulant.getCredential().getPassword()));
			return new ResponseEntity<>(postulantService.save(postulant), HttpStatus.CREATED);
		} catch (EmploymentException error) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
