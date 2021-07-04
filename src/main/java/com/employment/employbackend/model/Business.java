package com.employment.employbackend.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "business")
public class Business {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String aboutUs;

	@NotEmpty
	private String email;

	@OneToMany(mappedBy = "business")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "contractPeriod", "requirements", "salary",
			"responsabilities", "descriptionOffer", "validDate", "initWorkingDayTime", "endWorkingDayTime", "position",
			"vacancyNumbers", "business", "createdAt", "updatedAt" })
	private List<JobOffer> jobOfferList;
}
