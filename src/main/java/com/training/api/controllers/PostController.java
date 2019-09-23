package com.training.api.controllers;

import java.util.List;

import com.training.api.entitys.TblPost;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import com.training.api.services.PostService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Get all records in table tbl_post
     *
     */
    @RequestMapping(value = "/post/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<TblPost> tblPostList = postService.findAllPost();

        return new ResponseEntity(new RestData(tblPostList), HttpStatus.OK);
    }

    /**
     * Create record into table post
     *
     * @param tblPost object {@link TblPost}
     *
     */
    @RequestMapping(value = "/post/", method = RequestMethod.PUT)
    public ResponseEntity registerPost(@RequestBody TblPost tblPost){
        try {
            TblPost tblPostCreate = postService.create(tblPost);

            return new ResponseEntity<>(tblPostCreate, HttpStatus.OK);
        } catch (ConflicException e) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update city in table post by post id
     *
     * @param request object {@link TblPost}
     * @param postId post id
     *
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.PUT)
    public ResponseEntity updatePost(@RequestBody TblPost request, @PathVariable("postId") int postId){
        try {
            TblPost updateTblPost = postService.update(postId, request);

            return new ResponseEntity<>(updateTblPost, HttpStatus.OK);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete record when id mapping
     *
     * @param postId post id
     *
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePost(@PathVariable("postId") int postId){
        try {
            TblPost deletePost = postService.deletePost(postId);

            return new ResponseEntity<>(deletePost, HttpStatus.OK);
        } catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ApiMessage.error500(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Find city by city id
     *
     * @param postId city id
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public ResponseEntity findPostById(@PathVariable("postId") int postId){
        try {
            TblPost tblPost = postService.findPostById(postId);

            return new ResponseEntity<>(tblPost, HttpStatus.OK);
        } catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ApiMessage.error500(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }
}
