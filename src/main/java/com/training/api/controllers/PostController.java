package com.training.api.controllers;

import java.util.List;

import com.training.api.entitys.TblPost;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.RegisterPostRequest;
import com.training.api.models.UpdatePostRequest;
import com.training.api.services.PostService;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class PostController {


    private final PostService postService;

    /**
     * Function request processing when call url mapping
     *
     * @param postcode post code
     *
     * @return List of {@link SearchPostCodeResponse} found
     */
    @RequestMapping(value = "/post_offices/post/{postcode}", method = RequestMethod.GET)
    public ResponseEntity searchAddressByPostCode(@PathVariable("postcode") String postcode) {
        try {
            List<SearchPostCodeResponse> postCodeResponseList = postService.searchAddressByPostCode(postcode);

            return new ResponseEntity<>(new RestData(postCodeResponseList), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all {@link TblPost}
     *
     * @return List of {@link TblPost}
     */
    @RequestMapping(value = "/post/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<TblPost> tblPostList = postService.findAllPost();

        return new ResponseEntity(new RestData(tblPostList), HttpStatus.OK);
    }

    /**
     * Create new Post {@link TblPost}
     *
     * @param request The request to register {@link RegisterPostRequest}
     *
     * @return registed {@link TblPost}
     */
    @RequestMapping(value = "/post/", method = RequestMethod.PUT)
    public ResponseEntity registerPost(@Validated @RequestBody RegisterPostRequest request){
        try {
            TblPost postToRegister = request.get();
            TblPost tblPostCreate = postService.create(postToRegister);

            return new ResponseEntity<>(tblPostCreate, HttpStatus.OK);
        } catch (ConflicException e) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a existing {@link TblPost}
     *
     * @param request The request to update {@link UpdatePostRequest}
     *
     * @param postId Post id
     * @return
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.PUT)
    public ResponseEntity updatePost(@Validated @RequestBody UpdatePostRequest request, @PathVariable("postId") String postId){
        try {

            TblPost updateTblPost = postService.update(postId, request);

            return new ResponseEntity<>(updateTblPost, HttpStatus.OK);
        } catch (IllegalArgumentException | NotFoundException e) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        } catch (ConflicException e) {
            return new ResponseEntity<>(new ApiMessage("409", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * Delete post {@link TblPost}
     *
     * @param postId Post id
     * @return deleted {@link TblPost}
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePost(@PathVariable("postId") String postId){
        try {
            TblPost deletePost = postService.deletePost(postId);

            return new ResponseEntity<>(deletePost, HttpStatus.OK);
        } catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get city {@link TblPost}.
     *
     * @param postId Post id
     *
     * @return {@link TblPost} found
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public ResponseEntity getPost(@PathVariable("postId") String postId){
        try {
            TblPost tblPost = postService.findPostById(postId).orElseThrow(() -> new NotFoundException("City not found"));

            return new ResponseEntity<>(tblPost, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
