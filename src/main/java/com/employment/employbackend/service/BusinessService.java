package com.employment.employbackend.service;

import java.util.List;

import com.employment.employbackend.model.Business;
import com.employment.employbackend.rs.QuantityJobOfferByBusiness;

public interface BusinessService extends Crud<Business, Integer> {

	List<QuantityJobOfferByBusiness> getQuantityJobOfferByBusiness();
}
