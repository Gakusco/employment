package com.employment.employbackend.rs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmailPostulant implements Serializable {

	private static final long serialVersionUID = -8040817824043782601L;
	@JsonProperty("Web")
	private String web;
	@JsonProperty("Número de teléfono")
	private String phoneNumber;
	@JsonProperty("R.U.N")
	private String run;
	@JsonProperty("Correo")
	private String email;
	@JsonProperty("Fecha de nacimiento")
	private String dateOfBirth;
	@JsonProperty("Nombre")
	private String name;
	@JsonProperty("Apellido")
	private String lastName;
}
