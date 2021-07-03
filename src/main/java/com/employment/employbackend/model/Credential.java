package com.employment.employbackend.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
@Table(name = "credentials")
public class Credential implements Serializable {

	private static final long serialVersionUID = -8957941166836202760L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Pattern(regexp = "[a-zA-Z0-9.!%#&$'*+/^_-]{1,20}[@][a-zA-Z0-9-]{1,20}[.][a-z]{1,4}", message = "formato: jmartin@gmail.com")
	private String email;

	private String password;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "credencial_roles", joinColumns = {
			@JoinColumn(name = "id_credencial") }, inverseJoinColumns = @JoinColumn(name = "id_role"))
	private List<Role> roles;
}
