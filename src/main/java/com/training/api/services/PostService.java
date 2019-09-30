package com.training.api.services;

import com.training.api.entitys.Area;
import com.training.api.entitys.Post;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.Common;
import com.training.api.utils.exceptions.ConflictException;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.PostRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	
	private final AreaRepository areaRepository;
	
	private final ApiMessage apiMessage;
	
	
	/**
	 * Get address information by post code
	 *
	 * @param  postCode postcode;
	 *
	 * @return searchPostCodeResponseList List of {@link SearchPostCodeResponse}
	 */
	public List<SearchPostCodeResponse> searchAddressByPostCode(String postCode) throws NotFoundException {
		if (Common.checkValidNumber(postCode) == false) {
			throw new IllegalArgumentException(apiMessage.getMessageError("service.post.search.invalid_input"));
		}
		
		List<SearchPostCodeResponse> searchPostCodeResponseList =
				areaRepository.findByPost_PostCode(postCode).stream()
					.map(SearchPostCodeResponse::new).collect(Collectors.toList());
		
		if (searchPostCodeResponseList.size() == 0) {
			throw new NotFoundException(apiMessage.getMessageError("service.post.search.not_found"));
		}
		return searchPostCodeResponseList;
	}
	
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
			throw new IllegalArgumentException(apiMessage.getMessageError("service.post.find.post_code_invalid"));
		}
		
		return postRepository.findByPostCode(postCode);
	}
	
	/**
	 * Create new {@link Post}
	 *
	 * @param createPost The Post to post
	 * @return created Post
	 * @throws ConflictException if create failed
	 * @throws NullPointerException if argument is null
	 */
	public Post create(Post createPost) {
		Common.checkNotNull(createPost, "Post must not be null");
		Post createdPost;
		try {
			createdPost = postRepository.save(createPost);
		} catch (DataIntegrityViolationException e) {
			throw new ConflictException(apiMessage.getMessageError("service.post.create.conflict"));
		}
		
		return createdPost;
	}
	
	/**
	 * Delete existing {@link Post}
	 *
	 * @param deletePost {@link Post} delete
	 * @return deleted {@link Post}
	 */
	@Transactional(rollbackOn = Exception.class)
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
	 * @throws ConflictException if post failed
	 */
	public Post update(Post updatePost) {
		Common.checkNotNull(updatePost, "Post must not be null");
		
		Post updatedPost;
		try {
			updatedPost = postRepository.save(updatePost);
		} catch (DataIntegrityViolationException e) {
			throw new ConflictException(apiMessage.getMessageError("service.post.update.conflict"));
		}
		
		return updatedPost;
	}
}
