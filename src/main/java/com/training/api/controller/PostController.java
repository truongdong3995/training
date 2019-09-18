package com.training.api.controller;

import java.util.List;
import java.util.Optional;

import com.training.api.entity.TblPost;
import com.training.api.service.PostService;
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
    @RequestMapping(value = "/post/create/", method = RequestMethod.PUT)
    public ResponseEntity createCity(@RequestBody TblPost tblPost){
        try {
            if (postService.findPostById(tblPost.getPostId()).isPresent()) {
                return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
            }
            TblPost createPost = postService.savePost(tblPost);

            return new ResponseEntity<>(new RestData(createPost), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update city in table post by post id
     *
     * @param tblPostDetail object {@link TblPost}
     * @param postId post id
     *
     */
    @RequestMapping(value = "/post/update/{postId}", method = RequestMethod.PUT)
    public ResponseEntity updateCity(@RequestBody TblPost tblPostDetail, @PathVariable("postId") int postId){
        Optional<TblPost> tblPost = postService.findPostById(postId);
        if (tblPost.isPresent() == false) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
        tblPost.get().setPostCode(tblPostDetail.getPostCode());
        tblPost.get().setMultiArea(tblPostDetail.getMultiArea());
        TblPost updatePost = postService.savePost(tblPost.get());

        return new ResponseEntity<>(new RestData(updatePost), HttpStatus.OK);
    }

    /**
     * Delete record when id mapping
     *
     * @param postId post id
     *
     */
    @RequestMapping(value = "/post/delete/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCity(@PathVariable("postId") int postId){
        try {
            Optional<TblPost> tblPost = postService.findPostById(postId);
            if (tblPost.isPresent() == false) {
                return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
            }
            postService.deletePost(tblPost.get());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT, HttpStatus.OK);
        } catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ApiMessage.error500(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Find city by city id
     *
     * @param postId city id
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post/find/{postId}", method = RequestMethod.GET)
    public ResponseEntity findCityById(@PathVariable("postId") int postId){
        Optional<TblPost> tblPost = postService.findPostById(postId);

        if (tblPost.isPresent() == false) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tblPost.get(), HttpStatus.OK);
    }
}
