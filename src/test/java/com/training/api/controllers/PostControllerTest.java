package com.training.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.City;
import com.training.api.entitys.Post;

import static org.hamcrest.CoreMatchers.is;

import com.training.api.entitys.fixtures.CityFixtures;
import com.training.api.entitys.fixtures.PostFixtures;
import com.training.api.models.RegisterPostRequest;
import com.training.api.models.UpdateCityRequest;
import com.training.api.models.fixtures.RegisterPostRequestFixtures;
import com.training.api.models.fixtures.UpdateCityRequestFixtures;
import com.training.api.services.PostService;
import com.training.api.utils.exceptions.AlreadyExistsException;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link PostController}
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PostService postService;
	
	
	/**
	 * Test GET "/posts/{postCode}"
	 *
	 */
	@Test
	public void testGetPost() throws Exception {
		// setup
		Post tblPost = PostFixtures.createPost();
		when(postService.findPostByPostCode(anyString())).thenReturn(Optional.of(tblPost));
		
		// exercise
		mvc.perform(get("/posts/{postCode}", tblPost.getPostCode()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.post_code", is(tblPost.getPostCode())))
			.andExpect(jsonPath("$.update_show", is(tblPost.getUpdateShow())))
			.andExpect(jsonPath("$.change_reason", is(tblPost.getChangeReason())))
			.andExpect(jsonPath("$.multi_area", is(tblPost.getMultiArea())));
	}
	
	/**
	 * Test GET "/posts/{postCode}"
	 *
	 * @throws IllegalArgumentException exceptions
	 */
	@Test
	public void testGetPostCatchIAE() throws Exception {
		// setup
		Post tblPost = PostFixtures.createPost();
		doThrow(IllegalArgumentException.class).when(postService).findPostByPostCode(anyString());
		// exercise
		mvc.perform(MockMvcRequestBuilders.get("/posts/{postCode}", tblPost.getPostCode())
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test GET "/posts/{postCode}"
	 *
	 * @throws NotFoundException exceptions
	 */
	@Test
	public void testGetPostCatchNFE() throws Exception {
		// setup
		Post tblPost = PostFixtures.createPost();
		when(postService.findPostByPostCode(anyString())).thenReturn(Optional.empty());
		// exercise
		mvc.perform(MockMvcRequestBuilders.get("/posts/{postCode}", tblPost.getPostCode())
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isNotFound());
	}
	
	/**
	 * Test PUT "/city/"
	 *
	 */
	@Test
	public void testRegisterPost() throws Exception {
		// setup
		String postCode = "47314";
		RegisterPostRequest request = RegisterPostRequestFixtures.createRequest(postCode);
		
		Post tblPost = request.get();
		when(postService.create(any(Post.class))).thenReturn(tblPost);
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(post("/posts/")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.post_code", is(tblPost.getPostCode())))
			.andExpect(jsonPath("$.update_show", is(tblPost.getUpdateShow())))
			.andExpect(jsonPath("$.change_reason", is(tblPost.getChangeReason())))
			.andExpect(jsonPath("$.multi_area", is(tblPost.getMultiArea())));
	}
	
	/**
	 * Test PUT "/city/"
	 *
	 * @throws AlreadyExistsException exceptions
	 */
	@Test
	public void testRegisterPostThrowCE() throws Exception {
		// setup
		String postCode = "47314";
		RegisterPostRequest request = RegisterPostRequestFixtures.createRequest(postCode);
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		doThrow(AlreadyExistsException.class).when(postService).create(any(Post.class));
		
		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/posts/")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isConflict());
	}
	
	/**
	 * Test PUT "/city/"
	 *
	 * @throws NullPointerException exceptions
	 */
	@Test
	public void testRegisterPostThrowNPE() throws Exception {
		// setup
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(null);
		
		doThrow(NullPointerException.class).when(postService).create(any(Post.class));
		
		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/posts/")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test PUT "/posts/{postCode}"
	 *
	 */
	@Test
	public void testUpdatePost() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		
		Post tblPost = PostFixtures.createPost();
		when(postService.findPostByPostCode(anyString())).thenReturn(Optional.of(tblPost));
		when(postService.update(any(Post.class))).thenReturn(tblPost);
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(post("/posts/{postCode}", tblPost.getPostCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.post_code", is(tblPost.getPostCode())))
			.andExpect(jsonPath("$.update_show", is(tblPost.getUpdateShow())))
			.andExpect(jsonPath("$.change_reason", is(tblPost.getChangeReason())))
			.andExpect(jsonPath("$.multi_area", is(tblPost.getMultiArea())));
	}
	
	/**
	 * Test PUT "/posts/{postCode}"
	 *
	 * @throws IllegalArgumentException exceptions
	 */
	@Test
	public void testUpdatePostThrowIAE() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		Post tblPost = PostFixtures.createPost();
		
		when(postService.findPostByPostCode(anyString())).thenReturn(Optional.of(tblPost));
		doThrow(IllegalArgumentException.class).when(postService).update(any(Post.class));
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(post("/posts/{postCode}", tblPost.getPostCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test PUT "/posts/{postCode}"
	 *
	 * @throws AlreadyExistsException exceptions
	 */
	@Test
	public void testUpdatePostThrowCE() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		Post tblPost = PostFixtures.createPost();
		
		when(postService.findPostByPostCode(anyString())).thenReturn(Optional.of(tblPost));
		doThrow(AlreadyExistsException.class).when(postService).update(any(Post.class));
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(post("/posts/{postCode}", tblPost.getPostCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			// verify
			.andExpect(status().isConflict());
	}
	
	/**
	 * Test DELETE "/posts/{cityId}"
	 *
	 */
	@Test
	public void deleteCity() throws Exception {
		// setup
		Post tblPost = PostFixtures.createPost();
		when(postService.findPostByPostCode(anyString())).thenReturn(Optional.of(tblPost));
		when(postService.deletePost(any(Post.class))).thenReturn(tblPost);
		
		// exercise
		mvc.perform(delete("/posts/{cityId}", tblPost.getPostCode()))
			.andDo(print())
			// verify
			.andExpect(status().isOk());
	}
	
	/**
	 * Test DELETE "/posts/{cityId}" throws NotFoundException
	 *
	 */
	@Test
	public void deleteCityThrowsNFE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(postService.findPostByPostCode(anyString())).thenReturn(Optional.empty());
		// exercise
		mvc.perform(delete("/posts/{postCode}", city.getCityId()))
			.andDo(print())
			// verify
			.andExpect(status().isNotFound());
	}
}
