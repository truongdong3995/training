package com.training.api.repositorys;

import com.training.api.entitys.Post;
import com.training.api.entitys.fixtures.PostFixtures;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@TestExecutionListeners(value = FlywayTestExecutionListener.class, mergeMode = MERGE_WITH_DEFAULTS)
@ContextConfiguration(classes = {
	DataSourceAutoConfiguration.class,
	FlywayAutoConfiguration.class,
	ValidationAutoConfiguration.class
}, initializers = ConfigFileApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unittest")
public class PostRepositoryTest {
	
	@Autowired
	PostRepository sut;
	
	
	/**
	 * Test find Post by post code
	 *
	 */
	@Test
	@FlywayTest
	public void testFindByCode() {
		// setup
		Post post = PostFixtures.createPost();
		sut.save(post);
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
	 * Test insert/update post
	 *
	 */
	@Test
	@FlywayTest
	public void testSave() {
		// setup
		Post post = PostFixtures.createPost();
		// exercise
		Post registerPost = sut.save(post);
		Post actual = sut.findByPostCode(post.getPostCode()).get();
		// verify
		assertThat(actual).isNotNull();
		assertThat(actual.getPostCode()).isEqualTo(post.getPostCode());
		assertThat(actual.getChangeReason()).isEqualTo(post.getChangeReason());
		assertThat(actual.getMultiArea()).isEqualTo(post.getMultiArea());
		assertThat(actual.getUpdateShow()).isEqualTo(post.getUpdateShow());
	}
	
	/**
	 * Test save/update post throws DataIntegrityViolationException
	 *
	 */
	@Test
	@FlywayTest
	public void testCreateThrowsDIVE() {
		// setup
		Post post = PostFixtures.createPost();
		sut.save(post);
		// exercise & verify
		assertThatThrownBy(() -> sut.save(post))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	/**
	 * Test save/update post throws DataIntegrityViolationException
	 *
	 */
	@Test
	@FlywayTest
	public void testUpdateThrowsDIVE() {
		// setup
		Post post1 = PostFixtures.createPost();
		post1.setPostCode("1111");
		sut.save(post1);
		Post post2 = PostFixtures.createPost();
		post2.setPostCode("2222");
		sut.save(post2);
		post2.setPostCode(post1.getPostCode());
		// exercise & verify
		assertThatThrownBy(() -> sut.save(post2))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	/**
	 * Test delete
	 *
	 */
	@Test
	@FlywayTest
	public void testDelete() {
		// setup
		Post post = PostFixtures.createPost();
		sut.save(post);
		// exercise
		Optional<Post> actual = sut.findByPostCode(post.getPostCode());
		sut.delete(actual.get());
		//verify
		assertThat(sut.findByPostCode(actual.get().getPostCode())).isEmpty();
	}
}
