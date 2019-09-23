package com.training.api.services;

import com.training.api.entitys.TblPost;

import java.util.List;

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
    TblPost findPostById(int postId);

    /**
     * Save record in table tbl_post
     *
     * @param tblPost object {@link TblPost}
     */
    TblPost create (TblPost tblPost);

    /**
     * Save record in table tbl_post
     *
     * @param postId post id
     * @param tblPost object {@link TblPost}
     */
    TblPost update (int postId, TblPost tblPost);

    /**
     * Delete record in table tbl_post
     *
     * @param postId post id
     */
    TblPost deletePost(int postId);
}
