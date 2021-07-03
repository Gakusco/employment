package com.employment.employbackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "supervisors")
public class Supervisor extends User implements Serializable {

	private static final long serialVersionUID = -1360773431988927085L;

}
