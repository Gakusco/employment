package com.employment.employbackend.service;

import com.employment.employbackend.model.Credential;

public interface CredentialService extends Crud<Credential, Integer> {

	public Credential findByUsername(String username);
}
