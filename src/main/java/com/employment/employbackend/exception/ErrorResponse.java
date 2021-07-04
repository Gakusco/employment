package com.employment.employbackend.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = -8814094151476486784L;
	private String code;
	private String message;
	private String severity;

}
