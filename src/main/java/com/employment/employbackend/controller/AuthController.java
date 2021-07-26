package com.employment.employbackend.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employment.employbackend.exception.EmploymentException;
import com.employment.employbackend.helper.Constants;
import com.employment.employbackend.helper.ResponseValidate;
import com.employment.employbackend.model.Credential;
import com.employment.employbackend.model.Postulant;
import com.employment.employbackend.model.Role;
import com.employment.employbackend.service.CredentialService;
import com.employment.employbackend.service.PostulantService;
import com.employment.employbackend.service.RoleService;
import com.employment.employbackend.service.UploadFileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	@Autowired
	private PostulantService postulantService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private UploadFileService fileUploadFileService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody Postulant postulant, BindingResult result) {
		HashMap<String, List<String>> response = new HashMap<>();
		Credential credential = credentialService.findByUsername(postulant.getCredential().getUsername());
		if (result.hasErrors() || credential != null) {
			List<String> errList = ResponseValidate.resultErrorsToList(result);
			if (credential != null) {
				errList.add("El nombre de usuario ya existe");
			}
			response.put(ResponseValidate.ERROR, errList);
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

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		HashMap<String, List<String>> response = new HashMap<>();
		if (file.isEmpty()) {
			List<String> errors = new ArrayList<>();
			errors.add("Debe subir un archivo");
			response.put("error", errors);
			log.error("Debe subir un archivo");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			fileUploadFileService.saveFile(file);
		} catch (Exception e) {
			log.error("No se ha podido guardar el archivo: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<?> download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
		if (fileName.contains(".pdf")) {
			File file = new File(UploadFileService.UPLOAD_FOLDER + fileName);

			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=curriculum.pdf");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");

			Path path = Paths.get(file.getAbsolutePath());
			ByteArrayResource resource;
			try {
				resource = new ByteArrayResource(Files.readAllBytes(path));
				return ResponseEntity.ok().headers(header).contentLength(file.length())
						.contentType(MediaType.parseMediaType("application/pdf")).body(resource);
			} catch (IOException e) {
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Postulant postulant, BindingResult result) {
		HashMap<String, List<String>> response = new HashMap<>();
		if (result.hasErrors()) {
			response.put(ResponseValidate.ERROR, ResponseValidate.resultErrorsToList(result));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<Postulant> postulantOld = postulantService.findById(postulant.getId());
			if (postulantOld.isPresent()) {
				postulant.getCredential().setPassword(postulantOld.get().getCredential().getPassword());
				postulant.getCredential().setRoles(postulantOld.get().getCredential().getRoles());
				postulant.getCredential().setEnabled(postulantOld.get().getCredential().isEnabled());
				return new ResponseEntity<>(postulantService.save(postulant), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EmploymentException error) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/toggle-enable/{postulantId}")
	public ResponseEntity<?> delete(@PathVariable String postulantId) {
		try {
			Optional<Postulant> postulant = postulantService.findById(Integer.parseInt(postulantId));
			if (postulant.isPresent()) {
				postulant.get().getCredential().setEnabled(!postulant.get().getCredential().isEnabled());
				return new ResponseEntity<>(postulantService.save(postulant.get()), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EmploymentException error) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
