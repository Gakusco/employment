package com.employment.employbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employment.employbackend.exception.EmploymentException;
import com.employment.employbackend.helper.Constants;
import com.employment.employbackend.helper.ResponseValidate;
import com.employment.employbackend.model.JobOffer;
import com.employment.employbackend.model.Postulant;
import com.employment.employbackend.service.BusinessService;
import com.employment.employbackend.service.JobOfferService;
import com.employment.employbackend.service.PostulantService;

@RestController
@RequestMapping("/job-offer")
public class JobOfferController {

	@Autowired
	private JobOfferService jobOfferService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private PostulantService postulantService;

	@GetMapping("/list")
	public ResponseEntity<HashMap<String, List<JobOffer>>> findAll() {
		HashMap<String, List<JobOffer>> response = new HashMap<>();
		List<JobOffer> jobOfferList = jobOfferService.findAll();
		if (!jobOfferList.isEmpty()) {
			response.put("offerList", jobOfferList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/create/{businessId}")
	public ResponseEntity<?> create(@Valid @RequestBody JobOffer jobOffer, BindingResult result,
			@PathVariable String businessId) {
		HashMap<String, List<String>> response = new HashMap<>();
		if (result.hasErrors()) {
			response.put(ResponseValidate.ERROR, ResponseValidate.resultErrorsToList(result));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			businessService.findById(Integer.parseInt(businessId))
					.ifPresent(business -> jobOffer.setBusiness(business));
			return new ResponseEntity<>(jobOfferService.save(jobOffer), HttpStatus.CREATED);
		} catch (EmploymentException error) {
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody JobOffer jobOffer, BindingResult result) {
		HashMap<String, List<String>> response = new HashMap<>();
		if (result.hasErrors()) {
			response.put(ResponseValidate.ERROR, ResponseValidate.resultErrorsToList(result));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<JobOffer> jobOfferOld = jobOfferService.findById(jobOffer.getId());
			if (jobOfferOld.isPresent()) {
				jobOffer.setBusiness(jobOfferOld.get().getBusiness());
				jobOffer.setCreatedAt(jobOfferOld.get().getCreatedAt());
				return new ResponseEntity<>(jobOfferService.save(jobOffer), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EmploymentException error) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/toggle-enable/{jobOfferId}")
	public ResponseEntity<?> delete(@PathVariable String jobOfferId) {
		try {
			Optional<JobOffer> jobOffer = jobOfferService.findById(Integer.parseInt(jobOfferId));
			if (jobOffer.isPresent()) {
				jobOffer.get().setEnabled(!jobOffer.get().isEnabled());
				return new ResponseEntity<>(jobOfferService.save(jobOffer.get()), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EmploymentException error) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/applicants/list/{jobOfferId}")
	public ResponseEntity<?> findAllApplicants(@PathVariable String jobOfferId) {
		HashMap<String, List<Postulant>> response = new HashMap<>();
		Optional<List<Postulant>> postulantList = jobOfferService.findById(Integer.parseInt(jobOfferId))
				.map(JobOffer::getPostulantList);
		if (postulantList.isPresent()) {
			response.put("applicants", postulantList.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/postulate/{postulantId}/{jobOfferId}")
	public ResponseEntity<?> postulate(@PathVariable String postulantId, @PathVariable String jobOfferId) {

		HashMap<String, List<JobOffer>> response = new HashMap<>();
		HashMap<String, String> responseError = new HashMap<>();
		postulantService.findById(Integer.parseInt(postulantId))
				.ifPresent(postulant -> savePostulate(postulant, jobOfferId, response, responseError));
		if (response.containsKey(Constants.KEY_JOB_OFFER)) {
			return new ResponseEntity<>(response.get(Constants.KEY_JOB_OFFER), HttpStatus.CREATED);
		} else if (responseError.containsKey(Constants.KEY_ERROR)) {
			return new ResponseEntity<>(responseError, HttpStatus.OK);
		}
		responseError.put(Constants.KEY_ERROR, "Oferta de trabajo y/o postulante no existe");
		return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);

	}

	private void savePostulate(Postulant postulant, String jobOfferId, HashMap<String, List<JobOffer>> response,
			HashMap<String, String> responseError) {
		jobOfferService.findById(Integer.parseInt(jobOfferId)).ifPresent(
				jobOffer -> Optional.ofNullable(jobOffer).map(JobOffer::getPostulantList).ifPresent(postulantList -> {
					postulantList.add(postulant);
					try {
						findOfferByPostulantAndSave(postulant, jobOfferId, response, responseError, jobOffer);
					} catch (EmploymentException e) {
						responseError.put(Constants.KEY_ERROR, e.getMessage());
					}
				}));
	}

	private void findOfferByPostulantAndSave(Postulant postulant, String jobOfferId,
			HashMap<String, List<JobOffer>> response, HashMap<String, String> responseError, JobOffer jobOffer)
			throws EmploymentException {
		List<JobOffer> jobOfferPostulationOld = jobOfferService.findOffersByPostulantId(postulant.getId());
		jobOfferPostulationOld.forEach(jobOfferOld -> {
			if (jobOfferOld.getId() == Integer.parseInt(jobOfferId)) {
				responseError.put(Constants.KEY_ERROR, "Ya ha postulado a esta oferta de trabajo");
			}
		});
		if (!responseError.containsKey(Constants.KEY_ERROR)) {
			jobOfferService.save(jobOffer);
			response.put(Constants.KEY_JOB_OFFER, jobOfferService.findOffersByPostulantId(postulant.getId()));
		}

	}

}
