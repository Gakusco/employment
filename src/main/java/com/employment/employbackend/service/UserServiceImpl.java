package com.employment.employbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employment.employbackend.model.User;
import com.employment.employbackend.repository.UserRepository;

@Service
public class UserServiceImpl extends CrudImpl<User, Integer> implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> findByCredentialId(Integer credentialId) {
		return repository.findByCredentialId(credentialId);
	}

}
