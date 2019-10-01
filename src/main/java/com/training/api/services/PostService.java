package com.training.api.services;

import com.training.api.entitys.Area;
import com.training.api.entitys.Post;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.Common;
import com.training.api.utils.exceptions.AlreadyExistsException;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.PostRepository;
import com.training.api.validators.ModelValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	
	private final AreaRepository areaRepository;
	
	private final ApiMessage apiMessage;
	
	private final ModelValidator modelValidator;
	
	
	/**
	 * Get all {@link Post}
	 *
	 * @return List of {@link Post}
	 */
	public List<Post> findAllPost() {
		return postRepository.findAll();
	}
	
	/**
	 * Find single {@link Post}.
	 *
	 * @param postCode post code
	 *
	 * @return Found {@link Post}
	 * @throws IllegalArgumentException if post code invalid
	 */
	public Optional<Post> findPostByPostCode(String postCode) {
		if (Common.checkValidNumber(postCode) == false) {
			throw new IllegalArgumentException(
					apiMessage.getMessageError("service.post.find.post_code_invalid", postCode));
		}
		
		return postRepository.findByPostCode(postCode);
	}
	
	/**
	 * Create new {@link Post}
	 *
	 * @param createPost The Post to post
	 * @return created Post
	 * @throws AlreadyExistsException if create failed
	 * @throws NullPointerException if argument is null
	 */
	@Transactional
	public Post create(Post createPost) {
		Common.checkNotNull(createPost, "Post must not be null");
		Post createdPost;
		try {
			modelValidator.validate(createPost);
			createdPost = postRepository.save(createPost);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(
					apiMessage.getMessageError("service.post.create.area_already_exists", createPost.getPostCode()));
		}
		
		return createdPost;
	}
	
	/**
	 * Delete existing {@link Post}
	 *
	 * @param deletePost {@link Post} delete
	 * @return deleted {@link Post}
	 */
	@Transactional
	public Post deletePost(Post deletePost) {
		List<Area> tblAreaList = areaRepository.findByPost_PostId(Integer.valueOf(deletePost.getPostId()));
		if (tblAreaList.size() > 0) {
			areaRepository.deleteAll(tblAreaList);
		}
		postRepository.delete(deletePost);
		
		return deletePost;
	}
	
	/**
	 * Update existing {@link Post}
	 *
	 * @param updatePost {@link Post} update
	 * @return updatedPost updated post
	 * @throws NullPointerException if post is null
	 * @throws AlreadyExistsException if post failed
	 */
	@Transactional
	public Post update(Post updatePost) {
		Common.checkNotNull(updatePost, "Post must not be null");
		
		Post updatedPost;
		try {
			modelValidator.validate(updatePost);
			updatedPost = postRepository.save(updatePost);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(
					apiMessage.getMessageError("service.post.update.area_already_exists", updatePost.getPostCode()));
		}
		
		return updatedPost;
	}
}
