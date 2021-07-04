package com.employment.employbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employment.employbackend.model.JobOffer;

@Repository
public interface JobOfferRepository extends BaseRepository<JobOffer, Integer> {

	@Query(value = "SELECT id, created_at, updated_at, contract_period, description_offer, end_working_day_time, \r\n"
			+ " init_working_day_time, position, requirements, responsabilities, salary, \r\n"
			+ "        vacancy_numbers, valid_date, id_business \r\n" + "FROM job_offers JOIN \r\n"
			+ " (SELECT * FROM postulate WHERE postulate.id_postulant = ?1) as jobOfferPostulant", nativeQuery = true)
	List<JobOffer> findOffersByPostulantId(Integer postulantId);
}
