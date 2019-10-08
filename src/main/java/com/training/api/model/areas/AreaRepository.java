package com.training.api.model.areas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {
	
	/**
	 * Get list Area by city id
	 *
	 * @param cityId city id
	 *
	 * @return List of {@link Area}
	 */
	List<Area> findByCityCityId(int cityId);
	
	/**
	 * Get list Area by post code
	 *
	 * @param postCode post code
	 *
	 * @return List of {@link Area}
	 */
	List<Area> findByPostPostCode(String postCode);
	
	/**
	 * Get list Area by post id
	 *
	 * @param postId post_id
	 *
	 * @return List of {@link Area}
	 */
	List<Area> findByPostPostId(int postId);
}
