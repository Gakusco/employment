package com.employment.employbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.employment.employbackend.model.Business;
import com.employment.employbackend.rs.QuantityJobOfferByBusiness;

public interface BusinessRepository extends BaseRepository<Business, Integer> {

	@Query(value = "SELECT `name`, COUNT(*) as quantity\r\n"
			+ "FROM job_offers JOIN business on job_offers.id_business = business.id\r\n"
			+ "group by id_business", nativeQuery = true)
	List<QuantityJobOfferByBusiness> getQuantityJobOfferByBusiness();

}
