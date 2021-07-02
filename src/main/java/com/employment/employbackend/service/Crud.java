package com.employment.employbackend.service;

import java.util.List;
import java.util.Optional;

import com.employment.employbackend.exception.EmploymentException;

public interface Crud<E, ID> {

	E save(E entity) throws EmploymentException;
    Optional<E> findById(ID id);
    List<E> findAll();
    void deleteById(ID id) throws EmploymentException;
}
