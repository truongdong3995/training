package com.training.api.model.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link Post}s.
 *
 */
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	/**
	 * Find city by code.
	 *
	 * @param postCode post code
	 *
	 * @return Optional of {@link Post}
	 */
	Optional<Post> findByPostCode(String postCode);
}
