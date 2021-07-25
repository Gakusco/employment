package com.employment.employbackend.service;

import java.util.List;

import com.employment.employbackend.model.User;

public interface UserService extends Crud<User, Integer> {

	List<User> findByCredentialId(Integer credentialId);
}
