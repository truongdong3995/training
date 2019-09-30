package com.training.api.repositorys;

import com.training.api.entitys.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	/**
	 * Get list City by prefecture code
	 *
	 * @param prefectureCode prefecture code
	 *
	 * @return list of {@link City}
	 */
	List<City> findByPrefecture_PrefectureCode(String prefectureCode);
	
	/**
	 * Find City by code
	 *
	 * @param code city code
	 *
	 * @return Optional of {@link City}
	 */
	Optional<City> findByCode(String code);
}
