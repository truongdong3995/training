package com.training.api.model.posts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for {@link PostRepository}.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PostRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	PostRepository sut;
	
	
	/**
	 * Test find Post by post code.
	 *
	 */
	@Test
	public void testFindByCode() {
		// setup
		Post post = PostFixtures.createPost();
		// exercise
		Optional<Post> actual = sut.findByPostCode(post.getPostCode());
		// verify
		assertThat(actual).isPresent();
		Post postActual = actual.get();
		assertThat(postActual.getPostCode()).isEqualTo(post.getPostCode());
		assertThat(postActual.getChangeReason()).isEqualTo(post.getChangeReason());
		assertThat(postActual.getMultiArea()).isEqualTo(post.getMultiArea());
		assertThat(postActual.getUpdateShow()).isEqualTo(post.getUpdateShow());
	}
	
	/**
	 * Test insert/update post.
	 *
	 */
	@Test
	public void testSave() {
		// setup
		Post post = PostFixtures.createPost();
		// exercise
		Post actual = sut.findByPostCode(post.getPostCode()).get();
		// verify
		assertThat(actual).isNotNull();
		assertThat(actual.getPostCode()).isEqualTo(post.getPostCode());
		assertThat(actual.getChangeReason()).isEqualTo(post.getChangeReason());
		assertThat(actual.getMultiArea()).isEqualTo(post.getMultiArea());
		assertThat(actual.getUpdateShow()).isEqualTo(post.getUpdateShow());
	}
	
	/**
	 * Test save/update post throws DataIntegrityViolationException.
	 *
	 */
	@Test
	public void testCreateThrowsDIVE() {
		// setup
		Post post = PostFixtures.createPost();
		// exercise & verify
		assertThatThrownBy(() -> sut.save(post))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	/**
	 * Test delete.
	 *
	 */
	@Test
	public void testDelete() {
		// setup
		Post post = PostFixtures.createPost();
		// exercise
		Optional<Post> actual = sut.findByPostCode(post.getPostCode());
		sut.delete(actual.get());
		//verify
		assertThat(sut.findByPostCode(actual.get().getPostCode())).isEmpty();
	}
}
