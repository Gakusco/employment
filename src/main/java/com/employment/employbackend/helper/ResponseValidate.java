package com.employment.employbackend.helper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class ResponseValidate {
	public static final String MESSAGE_FIELD_ERROR = "error en uno o mas campos";
	public static final String USER_NO_REGISTER = "el usuario no esta registrado";
	public static final String BAD_EMAIL = "el correo no cohincide con el usuario registrado";
	public static final String SUCCESS_PROCESS = "proceso exitoso";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";

	public static List<String> resultErrorsToList(BindingResult result) {
		return result.getFieldErrors().stream()
				.map(err -> "el campo '" + err.getField() + "' : " + err.getDefaultMessage())
				.collect(Collectors.toList());
	}

	public static void messageError(Map<String, Object> response, String message, String error) {
		messagePut(response, message);
		errorPut(response, error);
	}

	public static void messagePut(Map<String, Object> response, String message) {
		response.put(MESSAGE, message);
	}

	public static void errorPut(Map<String, Object> response, String error) {
		response.put(ERROR, error);
	}
}
