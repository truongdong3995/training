package com.training.api.model.posts;

import com.training.api.model.areas.AreaRepository;
import com.training.api.utils.ApiMessage;
import com.training.api.model.AlreadyExistsException;
import com.training.api.model.InvalidModelException;
import com.training.api.validators.ModelValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test for {@link PostService}.
 */
@RunWith(SpringRunner.class)
public class PostServiceTest {
	
	private PostService sut;
	
	@Mock
	PostRepository postRepository;
	
	@Mock
	AreaRepository areaRepository;
	
	@Mock
	ApiMessage apiMessage;
	
	@Mock
	ModelValidator modelValidator;
	
	
	@Before
	public void setUp() {
		sut = new PostService(postRepository, areaRepository, apiMessage, modelValidator);
	}
	
	/**
	 * Test create new Post.
	 *
	 */
	@Test
	public void create() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doNothing().when(modelValidator).validate(any(Post.class));
		when(postRepository.save(any(Post.class))).thenReturn(tblPost);
		
		// exercise
		Post actual = sut.create(tblPost);
		// verify
		assertThat(actual).isEqualTo(tblPost);
	}
	
	/**
	 * Test create new Post throws NullPointerException.
	 *
	 */
	@Test
	public void createThrowsNPE() {
		// exercise
		assertThatThrownBy(() -> sut.create(null)).isInstanceOf(NullPointerException.class);
	}
	
	/**
	 * Test create new Post throws AlreadyExistsException.
	 *
	 */
	@Test
	public void createThrowsCE() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doNothing().when(modelValidator).validate(any(Post.class));
		doThrow(DataIntegrityViolationException.class).when(postRepository).save(any(Post.class));
		// exercise
		assertThatThrownBy(() -> sut.create(tblPost)).isInstanceOf(AlreadyExistsException.class);
	}
	
	/**
	 * Test create new Post throws InvalidModelException.
	 *
	 */
	@Test
	public void createThrowsIME() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doThrow(InvalidModelException.class).when(modelValidator).validate(any(Post.class));
		// exercise
		assertThatThrownBy(() -> sut.create(tblPost)).isInstanceOf(InvalidModelException.class);
	}
	
	/**
	 * Test find Post by
	 *
	 */
	@Test
	public void find() {
		// setup
		Post tblPost = PostFixtures.createPost();
		when(postRepository.findByPostCode(anyString())).thenReturn(Optional.of(tblPost));
		
		// exercise
		Optional<Post> actual = sut.find(tblPost.getPostCode());
		
		//verify
		assertThat(actual).isPresent();
		assertThat(actual.get()).isEqualTo(tblPost);
		verify(postRepository, times(1)).findByPostCode(tblPost.getPostCode());
	}
	
	/**
	 * Test find Post by post code throws IllegalArgumentException.
	 *
	 */
	@Test
	public void findPostByIdThrowIAE() {
		// setup
		String postCode = "TEST";
		// exercise
		assertThatThrownBy(() -> sut.find(postCode))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test update Post if exist.
	 *
	 */
	@Test
	public void update() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doNothing().when(modelValidator).validate(any(Post.class));
		when(postRepository.save(any(Post.class))).thenReturn(tblPost);
		// exercise
		Post actual = sut.update(tblPost);
		// verify
		assertThat(actual).isEqualTo(tblPost);
	}
	
	/**
	 * Test update Post if exist throws NullPointerException.
	 *
	 */
	@Test
	public void updateThrowsNPE() {
		// exercise
		assertThatThrownBy(() -> sut.update(null))
			.isInstanceOf(NullPointerException.class);
	}
	
	/**
	 * Test update Post if exist throws AlreadyExistsException.
	 *
	 */
	@Test
	public void updateThrowsCE() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doNothing().when(modelValidator).validate(any(Post.class));
		doThrow(DataIntegrityViolationException.class).when(postRepository).save(any(Post.class));
		// exercise
		assertThatThrownBy(() -> sut.update(tblPost))
			.isInstanceOf(AlreadyExistsException.class);
	}
	
	/**
	 * Test update Post if exist throws InvalidModelException.
	 *
	 */
	@Test
	public void updateThrowsIME() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doThrow(InvalidModelException.class).when(modelValidator).validate(any(Post.class));
		// exercise
		assertThatThrownBy(() -> sut.update(tblPost))
			.isInstanceOf(InvalidModelException.class);
	}
	
	/**
	 * Test delete Post if exist.
	 *
	 */
	@Test
	public void deletePost() {
		// setup
		Post tblPost = PostFixtures.createPost();
		when(areaRepository.findByCityCityId(anyInt())).thenReturn(new ArrayList<>());
		// exercise
		Post actual = sut.delete(tblPost);
		// verify
		verify(postRepository, times(1)).delete(tblPost);
		assertThat(actual).isEqualTo(tblPost);
	}
	
	/**
	 * Test delete Post if exist throws NullPointerException.
	 *
	 */
	@Test
	public void deleteThrowsNPE() {
		// exercise
		assertThatThrownBy(() -> sut.delete(null))
			.isInstanceOf(NullPointerException.class);
	}
}
