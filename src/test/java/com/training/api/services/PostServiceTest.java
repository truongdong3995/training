package com.training.api.services;

import com.training.api.entitys.Area;
import com.training.api.entitys.Post;
import com.training.api.entitys.fixtures.AreaFixtures;
import com.training.api.entitys.fixtures.PostFixtures;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.fixtures.SearchPostCodeResponseFixtures;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.PostRepository;
import com.training.api.utils.exceptions.ConflictException;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
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
	
	
	@Before
	public void setUp() {
		sut = new PostService(postRepository, areaRepository);
	}
	
	/**
	 * Test find all post
	 *
	 */
	@Test
	public void findAllPost() {
		// setup
		Post tblPost = PostFixtures.createPost();
		List<Post> tblPostList = new ArrayList<>();
		tblPostList.add(tblPost);
		Mockito.when(postRepository.findAll()).thenReturn(tblPostList);
		// exercise
		List<Post> actual = sut.findAllPost();
		// verify
		verify(postRepository, times(1)).findAll();
		assertThat(actual).isEqualTo(tblPostList);
	}
	
	/**
	 * Test search address by post code
	 *
	 */
	@Test
	public void searchAddressByPostCode() throws NotFoundException {
		// setup
		Area tblArea = AreaFixtures.createArea();
		SearchPostCodeResponse searchPostCodeResponse =
				SearchPostCodeResponseFixtures.createResponse(tblArea);
		List<Area> tblAreaList = new ArrayList<>();
		tblAreaList.add(tblArea);
		Mockito.when(areaRepository.findByTblPost_PostCode(anyString())).thenReturn(tblAreaList);
		// exercise
		List<SearchPostCodeResponse> actual =
				sut.searchAddressByPostCode(tblArea.getTblPost().getPostCode());
		// verify
		assertThat(actual.size()).isEqualTo(1);
	}
	
	/**
	 * Test search address by post code throws IllegalArgumentException
	 *
	 */
	@Test
	public void searchSearchAddressByPostCodeThrowIAE() {
		String postCode = "POST_CODE_TEST";
		// exercise
		assertThatThrownBy(() -> sut.searchAddressByPostCode(postCode))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test search address by post code throws NotFoundException
	 *
	 */
	@Test
	public void searchSearchAddressByPostCodeThrowNFE() {
		// setup
		Area tblArea = AreaFixtures.createArea();
		Mockito.when(areaRepository.findByTblPost_PostCode(anyString())).thenReturn(new ArrayList<>());
		// exercise
		assertThatThrownBy(() -> sut.searchAddressByPostCode(tblArea.getTblPost().getPostCode()))
			.isInstanceOf(NotFoundException.class);
	}
	
	/**
	 * Test find Post by id
	 *
	 */
	@Test
	public void findPostById() {
		// setup
		Post tblPost = PostFixtures.createPost();
		when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));
		
		// exercise
		Optional<Post> actual = sut.findPostById(String.valueOf(tblPost.getPostId()));
		
		//verify
		assertThat(actual).isPresent();
		assertThat(actual.get()).isEqualTo(tblPost);
		verify(postRepository, times(1)).findById(tblPost.getPostId());
	}
	
	/**
	 * Test find Post by id throws IllegalArgumentException
	 *
	 */
	@Test
	public void findPostByIdThrowIAE() {
		// setup
		String postCode = "TEST";
		// exercise
		assertThatThrownBy(() -> sut.findPostById(postCode))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test create new Post
	 *
	 */
	@Test
	public void create() {
		// setup
		Post tblPost = PostFixtures.createPost();
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
	 * Test create new Post throws ConflictException.
	 *
	 */
	@Test
	public void createThrowsCE() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doThrow(DataIntegrityViolationException.class).when(postRepository).save(any(Post.class));
		// exercise
		assertThatThrownBy(() -> sut.create(tblPost)).isInstanceOf(ConflictException.class);
	}
	
	/**
	 * Test update Post if exist.
	 *
	 */
	@Test
	public void update() {
		// setup
		Post tblPost = PostFixtures.createPost();
		Mockito.when(postRepository.save(any(Post.class))).thenReturn(tblPost);
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
	 * Test update Post if exist throws ConflictException.
	 *
	 */
	@Test
	public void updateThrowsCE() {
		// setup
		Post tblPost = PostFixtures.createPost();
		doThrow(DataIntegrityViolationException.class).when(postRepository).save(any(Post.class));
		// exercise
		assertThatThrownBy(() -> sut.update(tblPost))
			.isInstanceOf(ConflictException.class);
	}
	
	/**
	 * Test delete Post if exist.
	 *
	 */
	@Test
	public void deletePost() {
		// setup
		Post tblPost = PostFixtures.createPost();
		Mockito.when(areaRepository.findByCity_CityId(anyInt())).thenReturn(new ArrayList<>());
		// exercise
		Post actual = sut.deletePost(tblPost);
		// verify
		verify(postRepository, times(1)).delete(tblPost);
		assertThat(actual).isEqualTo(tblPost);
	}
}
