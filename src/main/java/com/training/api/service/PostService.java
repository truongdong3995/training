package com.training.api.service;

import com.training.api.entity.TblPost;

import java.util.List;
import java.util.Optional;

public interface PostService {

    /**
     * Get all records of table tbl_post
     *
     * @return List of {@link TblPost}
     */
    List<TblPost> findAllPost();

    /**
     * Find record mapping by id
     *
     * @param postId post id
     *
     * @return Optional of{@link TblPost}
     */
    Optional<TblPost> findPostById(int postId);

    /**
     * Save record in table tbl_post
     *
     * @param tblPost object {@link TblPost}
     */
    TblPost savePost (TblPost tblPost);

    /**
     * Delete record in table tbl_post
     *
     * @param tblPost Object {@link TblPost}
     */
    void deletePost(TblPost tblPost);

}
