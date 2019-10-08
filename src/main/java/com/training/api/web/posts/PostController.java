package com.training.api.web.posts;

import com.training.api.model.posts.Post;
import com.training.api.model.posts.PostService;
import com.training.api.model.AlreadyExistsException;
import com.training.api.utils.ApiMessage;
import com.training.api.model.InvalidModelException;
import javassist.NotFoundException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpBadRequestException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpConflictException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC RESTful Controller to handle {@link Post}s.
 *
 */
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
	 * Create new {@link Post}.
	 *
	 * @param request The request to create {@link Post}
	 *
	 * @return register {@link Post}
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity registerPost(
			@Validated @RequestBody RegisterPostRequest request) {
		try {
			Post postToRegister = request.get();
			Post tblPostCreate = postService.create(postToRegister);
			
			return ResponseEntity.ok(tblPostCreate);
		} catch (AlreadyExistsException e) {
			throw new HttpConflictException(e.getMessage(), e);
		} catch (InvalidModelException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		}
	}
	
	/**
	 * Get post {@link Post}.
	 *
	 * @param postCode Post code
	 *
	 * @return {@link Post} found
	 */
	@RequestMapping(value = "/{postCode}", method = RequestMethod.GET)
	public ResponseEntity getPost(
			@PathVariable("postCode") String postCode) {
		try {
			Post tblPost = postService.find(postCode).orElseThrow(
					() -> new NotFoundException(
							apiMessage.getMessageError("controller.post.get.post_not_found", postCode)));
			
			return ResponseEntity.ok(tblPost);
		} catch (IllegalArgumentException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		}
	}
	
	/**
	 * Update a existing {@link Post}.
	 *
	 * @param request The request to update {@link Post}
	 *
	 * @param postCode Post code
	 * @return updated {@link Post}
	 */
	@RequestMapping(value = "/{postCode}", method = RequestMethod.POST)
	public ResponseEntity updatePost(
			@Validated @RequestBody UpdatePostRequest request,
			@PathVariable("postCode") String postCode) {
		try {
			Post updateTblPost = postService.find(postCode)
				.map(request)
				.map(postService::update).orElseThrow(
						() -> new NotFoundException(
								apiMessage.getMessageError("controller.post.common.post_not_found", "update",
										postCode)));
			
			return ResponseEntity.ok(updateTblPost);
		} catch (IllegalArgumentException | InvalidModelException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		} catch (AlreadyExistsException e) {
			throw new HttpConflictException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		}
	}
	
	/**
	 * Delete {@link Post}.
	 *
	 * @param postCode Post code
	 * @return deleted {@link Post}
	 */
	@RequestMapping(value = "/{postCode}", method = RequestMethod.DELETE)
	public ResponseEntity deletePost(
			@PathVariable("postCode") String postCode) {
		try {
			Post deletePost = postService.find(postCode)
				.map(postService::delete).orElseThrow(
						() -> new NotFoundException(
								apiMessage.getMessageError("controller.post.common.post_not_found", "delete",
										postCode)));
			
			return ResponseEntity.ok(deletePost);
		} catch (IllegalArgumentException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		}
	}
}
