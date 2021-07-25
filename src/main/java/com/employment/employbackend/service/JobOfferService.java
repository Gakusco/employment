package com.employment.employbackend.service;

import java.util.List;

import com.employment.employbackend.model.JobOffer;

public interface JobOfferService extends Crud<JobOffer, Integer> {

	List<JobOffer> findOffersByPostulantId(Integer postulantId);

	List<JobOffer> findJobOffersByIdBusiness(Integer idInteger);
}
