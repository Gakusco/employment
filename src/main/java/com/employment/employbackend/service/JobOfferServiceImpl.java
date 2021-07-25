package com.employment.employbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employment.employbackend.model.JobOffer;
import com.employment.employbackend.repository.JobOfferRepository;

@Service
public class JobOfferServiceImpl extends CrudImpl<JobOffer, Integer> implements JobOfferService {

	@Autowired
	private JobOfferRepository repository;

	@Override
	public List<JobOffer> findOffersByPostulantId(Integer postulantId) {
		return repository.findOffersByPostulantId(postulantId);
	}

	@Override
	public List<JobOffer> findJobOffersByIdBusiness(Integer idInteger) {
		return repository.findJobOffersByIdBusiness(idInteger);
	}

}
