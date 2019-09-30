package com.training.api.repositorys;

import com.training.api.entitys.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	/**
	 * Find City by code
	 *
	 * @param postCode post code
	 *
	 * @return Optional of {@link Post}
	 */
	Optional<Post> findByPostCode(String postCode);
}
