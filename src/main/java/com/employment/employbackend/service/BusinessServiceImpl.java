package com.employment.employbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employment.employbackend.model.Business;
import com.employment.employbackend.repository.BusinessRepository;
import com.employment.employbackend.rs.QuantityJobOfferByBusiness;

@Service
public class BusinessServiceImpl extends CrudImpl<Business, Integer> implements BusinessService {

	@Autowired
	private BusinessRepository repository;

	@Override
	public List<QuantityJobOfferByBusiness> getQuantityJobOfferByBusiness() {
		return repository.getQuantityJobOfferByBusiness();
	}

}
