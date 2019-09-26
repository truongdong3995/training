package com.training.api.services;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblPost;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.UpdatePostRequest;
import com.training.api.utils.Common;
import com.training.api.utils.exceptions.ConflicException;
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

    /**
     * Get address information by post code
     *
     * @param  postCode postcode;
     *
     * @return searchPostCodeResponseList List of {@link SearchPostCodeResponse}
     */
    public List<SearchPostCodeResponse> searchAddressByPostCode(String postCode) throws NotFoundException {
        if (Common.checkValidNumber(postCode) == false) {
            throw new IllegalArgumentException("Post code phải là số halfsize");
        }

        List<SearchPostCodeResponse> searchPostCodeResponseList = areaRepository.findByTblPost_PostCode(postCode).stream()
                .map(SearchPostCodeResponse::new).collect(Collectors.toList());

        if (searchPostCodeResponseList.size() == 0) {
            throw new NotFoundException("Not found result");
        }
        return searchPostCodeResponseList;
    }

    /**
     * Get all {@link TblPost}
     *
     * @return List of {@link TblPost}
     */
    public List<TblPost> findAllPost() {
        return postRepository.findAll();
    }

    /**
     * Find single {@link TblPost}.
     *
     * @param postId post id
     *
     * @return Found {@link TblPost}
     * @throws IllegalArgumentException if TblPost invalid
     */
    public Optional<TblPost> findPostById(String postId) {
        if (Common.checkValidNumber(postId) == false) {
            throw new IllegalArgumentException("Post id phải là số halfsize");
        }

        return postRepository.findById(Integer.valueOf(postId));
    }

    /**
     * Create new {@link TblPost}
     *
     * @param createPost The Post to post
     * @return created Post
     * @throws ConflicException if create failed
     * @throws NullPointerException if argument is null
     */
    public TblPost create(TblPost createPost) {
        Common.checkNotNull(createPost, "City must not be null");
        TblPost createdPost;
        try {
            createdPost = postRepository.save(createPost);
        } catch (DataIntegrityViolationException e) {
            throw new ConflicException("Post has been exist");
        }

        return createdPost;
    }

    /**
     * Delete existing {@link TblPost}
     *
     * @param postId post id
     * @return deleted {@link TblPost}
     * @throws NullPointerException if postId is null
     * @throws NotFoundException if post is not found
     */
    @Transactional(rollbackOn = Exception.class)
    public TblPost deletePost(String postId) throws NotFoundException {
        TblPost tblPost = findPostById(postId).orElseThrow(()-> new NotFoundException("Post not found"));
        List<TblArea> tblAreaList= areaRepository.findByTblPost_PostId(Integer.valueOf(postId));
        if (tblAreaList.size() > 0) {
            areaRepository.deleteAll(tblAreaList);
        }
        postRepository.delete(tblPost);

        return tblPost;
    }

    /**
     * Update existing {@link TblPost}
     *
     * @param postId post id
     * @param request The request to update
     * @return updatedPost updated post
     * @throws NotFoundException if post is not found
     * @throws NullPointerException if post is null
     * @throws ConflicException if post failed
     */
    public TblPost update(String postId, UpdatePostRequest request) throws NotFoundException {
        TblPost tblPost = findPostById(postId).map(request).orElseThrow(()-> new NotFoundException("City not found"));
        Common.checkNotNull(request, "City must not be null");

        tblPost.setPostCode(request.getPostCode());
        tblPost.setMultiArea(request.getMultiArea());
        tblPost.setChangeReason(request.getChangeReason());
        TblPost updatedPost;
        try {
            updatedPost = postRepository.save(tblPost);
        } catch (DataIntegrityViolationException e) {
            throw new ConflicException("Post has been exist");
        }

        return updatedPost;
    }
}
