package com.training.api.controllers;

import java.util.List;

import com.training.api.entitys.Post;
import com.training.api.models.HttpExceptionResponse;
import com.training.api.models.RegisterPostRequest;
import com.training.api.models.UpdatePostRequest;
import com.training.api.services.PostService;
import com.training.api.utils.exceptions.AlreadyExistsException;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import com.training.api.utils.exceptions.InvalidModelException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional
@Slf4j
@Validated
@RequestMapping(value = "/posts")
public class PostController {
	
	private final PostService postService;
	
	private final ApiMessage apiMessage;
	
	
	/**
	 * Get all {@link Post}
	 *
	 * @return List of {@link Post}
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity getAll() {
		List<Post> tblPostList = postService.findAllPost();
		RestData response = new RestData(tblPostList);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	/**
	 * Get city {@link Post}.
	 *
	 * @param postCode Post code
	 *
	 * @return {@link Post} found
	 */
	@RequestMapping(value = "/{postCode}", method = RequestMethod.GET)
	public ResponseEntity getPost(@PathVariable("postCode") String postCode) {
		try {
			Post tblPost = postService.findPostByPostCode(postCode).orElseThrow(
					() -> new NotFoundException(
							apiMessage.getMessageError("controller.post.get.post_not_found", postCode)));
			
			return new ResponseEntity<>(tblPost, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Create new Post {@link Post}
	 *
	 * @param request The request to register {@link RegisterPostRequest}
	 *
	 * @return register {@link Post}
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity registerPost(@Validated @RequestBody RegisterPostRequest request) {
		try {
			Post postToRegister = request.get();
			Post tblPostCreate = postService.create(postToRegister);
			
			return new ResponseEntity<>(tblPostCreate, HttpStatus.OK);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("409", e.getMessage()), HttpStatus.CONFLICT);
		} catch (InvalidModelException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Update a existing {@link Post}
	 *
	 * @param request The request to update {@link UpdatePostRequest}
	 *
	 * @param postCode Post code
	 * @return updated {@link Post}
	 */
	@RequestMapping(value = "/{postCode}", method = RequestMethod.POST)
	public ResponseEntity updatePost(@Validated @RequestBody UpdatePostRequest request,
			@PathVariable("postCode") String postCode) {
		try {
			Post updateTblPost = postService.findPostByPostCode(postCode)
				.map(request)
				.map(postService::update).orElseThrow(
						() -> new NotFoundException(
								apiMessage.getMessageError("controller.post.update.post_not_found", postCode)));
			
			return new ResponseEntity<>(updateTblPost, HttpStatus.OK);
		} catch (IllegalArgumentException | InvalidModelException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("409", e.getMessage()), HttpStatus.CONFLICT);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Delete post {@link Post}
	 *
	 * @param postCode Post code
	 * @return deleted {@link Post}
	 */
	@RequestMapping(value = "/{postCode}", method = RequestMethod.DELETE)
	public ResponseEntity deletePost(@PathVariable("postCode") String postCode) {
		try {
			Post deletePost = postService.findPostByPostCode(postCode)
				.map(postService::deletePost).orElseThrow(
						() -> new NotFoundException(
								apiMessage.getMessageError("controller.post.delete.post_not_found", postCode)));
			
			return new ResponseEntity<>(deletePost, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
