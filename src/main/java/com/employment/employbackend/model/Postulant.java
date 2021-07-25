package com.employment.employbackend.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "applicants")
public class Postulant extends User implements Serializable {
	private static final long serialVersionUID = 2392020139154313085L;

	private String web;

	@Pattern(regexp = "[+][\\d]{11}", message = "Formato: +56934231287")
	private String phoneNumber;

	@Pattern(regexp = "[\\d]{1,2}[.][\\d]{3}[.][\\d]{3}[-][\\d|k|K]", message = "formato: 14.234.345-k")
	@NotEmpty
	private String run;

	@Pattern(regexp = "[a-zA-Z0-9.!%#&$'*+/^_-]{1,20}[@][a-zA-Z0-9-]{1,20}[.][a-z]{1,4}", message = "formato: jmartin@gmail.com")
	@NotEmpty
	private String email;

	@NotNull(message = "Debe ingresar su fecha de nacimiento")
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	private String curriculumVitae;
}
