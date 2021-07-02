package com.employment.employbackend.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "applicants")
public class Postulant extends User implements Serializable {
	private static final long serialVersionUID = 2392020139154313085L;
	
	private String web;
	private String phoneNumber;
	private String email;
	private String run;
	private LocalDate dateOfBirth;
	private String curriculumVitae;
}
