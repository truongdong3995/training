package com.training.api.model.posts;

import com.amazonaws.util.CollectionUtils;
import com.training.api.model.areas.Area;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.Common;
import com.training.api.model.AlreadyExistsException;
import com.training.api.model.areas.AreaRepository;
import com.training.api.validators.ModelValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for {@link Post}.
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	
	private final AreaRepository areaRepository;
	
	private final ApiMessage apiMessage;
	
	private final ModelValidator modelValidator;
	
	
	/**
	 * Create new {@link Post}.
	 *
	 * @param createPost The Post to post
	 * @return created Post
	 * @throws AlreadyExistsException if create failed
	 * @throws NullPointerException if argument is null
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Post create(Post createPost) {
		Common.checkNotNull(createPost, "Post must not be null");
		Post post = createPost.toBuilder().build();
		
		Post createdPost;
		try {
			modelValidator.validate(post);
			createdPost = postRepository.save(post);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(
					apiMessage.getMessageError("service.post.common.area_already_exists", "create",
							post.getPostCode()));
		}
		
		return createdPost;
	}
	
	/**
	 * Find single {@link Post}.
	 *
	 * @param postCode post code
	 *
	 * @return Found {@link Post}
	 * @throws IllegalArgumentException if post code invalid
	 */
	public Optional<Post> find(String postCode) {
		if (Common.checkValidNumber(postCode) == false) {
			throw new IllegalArgumentException(
					apiMessage.getMessageError("service.post.find.post_code_invalid", postCode));
		}
		
		return postRepository.findByPostCode(postCode);
	}
	
	/**
	 * Update existing {@link Post}.
	 *
	 * @param updatePost {@link Post} update
	 * @return updatedPost updated post
	 * @throws NullPointerException if post is null
	 * @throws AlreadyExistsException if post failed
	 */
	@Transactional
	public Post update(Post updatePost) {
		Common.checkNotNull(updatePost, "Post must not be null");
		Post post = updatePost.toBuilder().build();
		
		Post updatedPost;
		try {
			modelValidator.validate(post);
			updatedPost = postRepository.save(post);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(
					apiMessage.getMessageError("service.post.common.area_already_exists", "update",
							post.getPostCode()));
		}
		
		return updatedPost;
	}
	
	/**
	 * Delete existing {@link Post}.
	 *
	 * @param deletePost {@link Post} delete
	 * @return deleted {@link Post}
	 */
	@Transactional
	public Post delete(Post deletePost) {
		Common.checkNotNull(deletePost, "Post must not be null");
		List<Area> areaList = areaRepository.findByPostPostId(deletePost.getPostId());
		if (CollectionUtils.isNullOrEmpty(areaList) == false) {
			areaRepository.deleteAll(areaList);
		}
		postRepository.delete(deletePost);
		
		return deletePost;
	}
	
}
