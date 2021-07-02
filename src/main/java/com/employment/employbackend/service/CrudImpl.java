package com.employment.employbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.employment.employbackend.exception.EmploymentException;
import com.employment.employbackend.repository.BaseRepository;

public abstract class CrudImpl<E, ID> implements Crud<E, ID> {

	@Autowired
    private BaseRepository<E, ID> repository;

    @Override
    public E save(E entity) throws EmploymentException {
        if (entity == null) throw new EmploymentException();
        try {
            return repository.save(entity);
        } catch (DataAccessException e) {
            throw new EmploymentException();
        }
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(ID id) throws EmploymentException {
        Optional<E> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            repository.delete(entityOptional.get());
        } else {
            throw new EmploymentException();
        }
    }
}
