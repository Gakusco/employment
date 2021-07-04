package com.employment.employbackend.repository;

import com.employment.employbackend.model.Credential;

public interface CredentialRepository extends BaseRepository<Credential, Integer> {

	public Credential findByUsername(String username);
}
