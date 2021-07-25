package com.employment.employbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employment.employbackend.model.User;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

	@Query(value = "(SELECT * FROM users WHERE id_credencial = ?1)", nativeQuery = true)
	List<User> findByCredentialId(Integer credentialId);
}
