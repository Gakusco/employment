package com.employment.employbackend.controller;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.employment.employbackend.model.Business;
import com.employment.employbackend.model.JobOffer;
import com.employment.employbackend.model.Postulant;
import com.employment.employbackend.rs.EmailPostulant;
import com.employment.employbackend.service.BusinessService;
import com.employment.employbackend.service.JobOfferService;
import com.employment.employbackend.service.MailService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/send")
public class SendEmailController {

	@Autowired
	private MailService mailService;

	@Autowired
	private JobOfferService jobOfferService;

	@Autowired
	private BusinessService businessService;

	@GetMapping("/all/{jobOfferId}")
	public ResponseEntity<?> sendEmail(@PathVariable String jobOfferId) {
		HashMap<String, List<Postulant>> response = new HashMap<>();
		Optional<JobOffer> jobOffer = jobOfferService.findById(Integer.parseInt(jobOfferId));
		Optional<List<Postulant>> postulantList = jobOffer.map(JobOffer::getPostulantList);

		if (postulantList.isPresent() && jobOffer.isPresent()) {
			Optional<Business> business = businessService.findById(jobOffer.get().getBusiness().getId());
			if (business.isPresent()) {
				response.put("applicants", postulantList.get());
				Gson gson = new Gson();
				List<EmailPostulant> emailPostulantList = postulantList.get().stream()
						.map(this::postulantToEmailPostulant).collect(Collectors.toList());
				String applicants = gson.toJson(emailPostulantList);
				applicants = applicants.replace("\"", "").replace("[{", "").replace("}]", "\n").replace("web", "\nweb")
						.replace("phoneNumber", "\nNúmero de teléfono").replace("run", "\nR.U.N")
						.replace("email", "\nCorreo").replace("dateOfBirth", "\nFecha de nacimiento")
						.replace("name", "\nNombre").replace("lastName", "\nApellido");
				if (emailPostulantList.size() > 1) {
					applicants = applicants.replace("},{", "\n");
				}
				try {
					String emails[] = postulantList.get().stream().map(Postulant::getCurriculumVitae)
							.collect(Collectors.toList()).toArray(new String[0]);
					mailService.sendMailWithAttachment(business.get().getEmail(), "Postulantes oferta de trabajo",
							applicants, emails);
				} catch (MessagingException e) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>("OK", HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	private EmailPostulant postulantToEmailPostulant(Postulant postulant) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		EmailPostulant emailPostulant = new EmailPostulant();
		emailPostulant.setDateOfBirth(postulant.getDateOfBirth().format(formatter));
		emailPostulant.setEmail(postulant.getEmail());
		emailPostulant.setName(postulant.getName());
		emailPostulant.setLastName(postulant.getLastName());
		emailPostulant.setPhoneNumber(postulant.getPhoneNumber());
		emailPostulant.setRun(postulant.getRun());
		emailPostulant.setWeb(postulant.getWeb());
		return emailPostulant;
	}
}
