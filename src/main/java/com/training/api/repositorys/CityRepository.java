package com.training.api.repositorys;

import com.training.api.entitys.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	/**
	 * Get list City by prefecture code
	 *
	 * @param prefectureCode prefecture code
	 *
	 * @return list of {@link City}
	 */
	List<City> findByTblPrefecture_PrefectureCode(String prefectureCode);
}
