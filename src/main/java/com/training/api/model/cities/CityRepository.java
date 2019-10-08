package com.training.api.model.cities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link City}s.
 *
 */
public interface CityRepository extends JpaRepository<City, Integer> {
	
	/**
	 * Get list City by prefecture code.
	 *
	 * @param prefectureCode prefecture code
	 *
	 * @return list of {@link City}
	 */
	List<City> findByPrefecturePrefectureCode(String prefectureCode);
	
	/**
	 * Find City by code.
	 *
	 * @param cityCode city code
	 *
	 * @return Optional of {@link City}
	 */
	Optional<City> findByCityCode(String cityCode);
}
