package com.employment.employbackend.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "job_offers")
@EqualsAndHashCode(callSuper = true)
public class JobOffer extends CreatedAtUpdatedAt implements Serializable {

	private static final long serialVersionUID = -8520998104531536956L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	private String contractPeriod;

	@NotEmpty
	private String requirements;

	@NotNull
	private Integer salary;

	@NotEmpty
	private String responsabilities;

	@NotEmpty
	private String descriptionOffer;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate validDate;

	@NotEmpty
	private String initWorkingDayTime;

	@NotEmpty
	private String endWorkingDayTime;

	@NotEmpty
	private String position;

	@NotNull
	private Integer vacancyNumbers;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "postulate", joinColumns = {
			@JoinColumn(name = "id_job_offer") }, inverseJoinColumns = @JoinColumn(name = "id_postulant"))
	@JsonIgnore
	private List<Postulant> postulantList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_business")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "jobOfferList" })
	private Business business;
}
