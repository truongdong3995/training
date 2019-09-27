package com.training.api.controllers;

import java.util.List;

import com.training.api.entitys.Post;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.RegisterPostRequest;
import com.training.api.models.UpdatePostRequest;
import com.training.api.services.PostService;
import com.training.api.utils.exceptions.ConflictException;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Transactional
@Slf4j
@Validated
public class PostController {
	
	private final PostService postService;
	
	
	/**
	 * Function request processing when call url mapping
	 *
	 * @param postcode post code
	 *
	 * @return List of {@link SearchPostCodeResponse} found
	 */
	@RequestMapping(value = "/post_offices/post/{postcode}", method = RequestMethod.GET)
	public ResponseEntity searchAddressByPostCode(@PathVariable("postcode") String postcode) {
		try {
			List<SearchPostCodeResponse> postCodeResponseList = postService.searchAddressByPostCode(postcode);
			
			return new ResponseEntity<>(new RestData(postCodeResponseList), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Get all {@link Post}
	 *
	 * @return List of {@link Post}
	 */
	@RequestMapping(value = "/posts/", method = RequestMethod.GET)
	public ResponseEntity getAll() {
		List<Post> tblPostList = postService.findAllPost();
		
		return new ResponseEntity(new RestData(tblPostList), HttpStatus.OK);
	}
	
	/**
	 * Get city {@link Post}.
	 *
	 * @param postId Post id
	 *
	 * @return {@link Post} found
	 */
	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
	public ResponseEntity getPost(@PathVariable("postId") String postId) {
		try {
			Post tblPost = postService.findPostById(postId).orElseThrow(() -> new NotFoundException("City not found"));
			
			return new ResponseEntity<>(tblPost, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Create new Post {@link Post}
	 *
	 * @param request The request to register {@link RegisterPostRequest}
	 *
	 * @return register {@link Post}
	 */
	@RequestMapping(value = "/posts", method = RequestMethod.PUT)
	public ResponseEntity registerPost(@Validated @RequestBody RegisterPostRequest request) {
		try {
			Post postToRegister = request.get();
			Post tblPostCreate = postService.create(postToRegister);
			
			return new ResponseEntity<>(tblPostCreate, HttpStatus.OK);
		} catch (ConflictException e) {
			return new ResponseEntity<>(new ApiMessage("409", e.getMessage()), HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * Update a existing {@link Post}
	 *
	 * @param request The request to update {@link UpdatePostRequest}
	 *
	 * @param postId Post id
	 * @return
	 */
	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.POST)
	public ResponseEntity updatePost(@Validated @RequestBody UpdatePostRequest request,
			@PathVariable("postId") String postId) {
		try {
			Post updateTblPost = postService.findPostById(postId)
				.map(request)
				.map(post -> postService.update(post))
				.orElseThrow(() -> new NotFoundException("City no found"));
			
			return new ResponseEntity<>(updateTblPost, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ConflictException e) {
			return new ResponseEntity<>(new ApiMessage("409", e.getMessage()), HttpStatus.CONFLICT);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Delete post {@link Post}
	 *
	 * @param postId Post id
	 * @return deleted {@link Post}
	 */
	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.DELETE)
	public ResponseEntity deletePost(@PathVariable("postId") String postId) {
		try {
			Post deletePost = postService.findPostById(postId)
				.map(post -> postService.deletePost(post))
				.orElseThrow(() -> new NotFoundException("City no found"));
			
			return new ResponseEntity<>(deletePost, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
