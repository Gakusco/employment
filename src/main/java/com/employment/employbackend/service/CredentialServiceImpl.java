package com.employment.employbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employment.employbackend.model.Credential;
import com.employment.employbackend.repository.CredentialRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CredentialServiceImpl extends CrudImpl<Credential, Integer>
		implements CredentialService, UserDetailsService {

	@Autowired
	private CredentialRepository repository;

	@Override
	public Credential findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Credential credential = repository.findByUsername(username);
		if (credential == null) {
			log.error(username + " not exist");
			throw new UsernameNotFoundException(username + " not exist");
		}
		List<GrantedAuthority> authorities = credential.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
		return new User(credential.getUsername(), credential.getPassword(), credential.isEnabled(), true, true, true,
				authorities);
	}

}
