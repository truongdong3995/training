package com.training.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPost;

import static org.hamcrest.CoreMatchers.is;

import com.training.api.entitys.fixtures.TblAreaFixtures;
import com.training.api.entitys.fixtures.TblCityFixtures;
import com.training.api.entitys.fixtures.TblPostFixtures;
import com.training.api.models.RegisterPostRequest;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.fixtures.RegisterCityRequestFixtures;
import com.training.api.models.fixtures.RegisterPostRequestFixtures;
import com.training.api.services.PostService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.ConflicException;
import javassist.NotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link PostController}
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    private TblPost tblPost;

    /**
     * Test GET "post_offices/prefectures/{postCode}"
     *
     */
    @Test
    public void testSearchAddressByPostCode() throws Exception {
        // setup
        TblArea tblArea = TblAreaFixtures.createArea();
        SearchPostCodeResponse response = new SearchPostCodeResponse(tblArea);
        List<SearchPostCodeResponse> responseList = new ArrayList<>();
        responseList.add(response);

        when(postService.searchAddressByPostCode(anyString())).thenReturn(responseList);

        // exercise
        mvc.perform(get("/post_offices/prefectures/{postCode}",
                tblArea.getTblPost().getPostCode())
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].area", is(tblArea.getArea())))
                .andExpect(jsonPath("$.data[0].area_kana", is(tblArea.getAreaKana())))
                .andExpect(jsonPath("$.data[0].city", is(tblArea.getTblCity().getCity())))
                .andExpect(jsonPath("$.data[0].city_kana", is(tblArea.getTblCity().getCityKana())));
    }

    /**
     * Test GET "post_offices/prefectures/{postCode}"
     *
     * @throws IllegalArgumentException exceptions
     */
    @Test
    public void testSearchAddressByPostCodeThrowIAE() throws Exception {
        // setup
        TblArea tblArea = TblAreaFixtures.createArea();
        doThrow(IllegalArgumentException.class).when(postService).searchAddressByPostCode(anyString());

        // exercise
        mvc.perform(get("/post_offices/prefectures/{postCode}",
                tblArea.getTblPost().getPostCode())
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())));
    }

    /**
     * Test GET "post_offices/prefectures/{postCode}"
     *
     * @throws NotFoundException exceptions
     */
    @Test
    public void testSearchAddressByPostCodeThrowNFE() throws Exception {
        // setup
        TblArea tblArea = TblAreaFixtures.createArea();
        doThrow(NotFoundException.class).when(postService).searchAddressByPostCode(anyString());

        // exercise
        mvc.perform(get("/post_offices/prefectures/{postCode}",
                tblArea.getTblPost().getPostCode())
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())));
    }

    /**
     * Test GET "/post/{postId}"
     *
     */
    @Test
    public void testGetPost() throws Exception {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        when(postService.findPostById(anyString())).thenReturn(Optional.of(tblPost));

        // exercise
        mvc.perform(get("/post/{postId}", tblPost.getPostId()).contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.post_code", is(tblPost.getPostCode())))
                .andExpect(jsonPath("$.update_show", is(tblPost.getUpdateShow())))
                .andExpect(jsonPath("$.change_reason", is(tblPost.getChangeReason())))
                .andExpect(jsonPath("$.multi_area", is(tblPost.getMultiArea())));
    }

    /**
     * Test GET "/post/{postId}"
     *
     * @throws IllegalArgumentException exceptions
     */
    @Test
    public void testGetPostCatchIAE() throws Exception {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        doThrow(IllegalArgumentException.class).when(postService).findPostById(anyString());
        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post/{postId}", tblPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())));
        ;
    }

    /**
     * Test GET "/post/{postId}"
     *
     * @throws NotFoundException exceptions
     */
    @Test
    public void testGetPostCatchNFE() throws Exception {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        when(postService.findPostById(anyString())).thenReturn(Optional.empty());
        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post/{postId}", tblPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound());
        ;
    }

    /**
     * Test PUT "/city/"
     *
     */
    @Test
    public void testRegisterPost() throws Exception {
        // setup
        RegisterPostRequest request = RegisterPostRequestFixtures.createRequest();

        TblPost tblPost = request.get();
        when(postService.create(any())).thenReturn(tblPost);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(put("/city/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.post_code", is(tblPost.getPostCode())))
                .andExpect(jsonPath("$.update_show", is(tblPost.getUpdateShow())))
                .andExpect(jsonPath("$.change_reason", is(tblPost.getChangeReason())))
                .andExpect(jsonPath("$.multi_area", is(tblPost.getMultiArea())));
    }

    /**
     * Test PUT "/city/"
     *
     * @throws ConflicException exceptions
     */
    @Test
    public void testRegisterPostThrowCE() throws Exception {
        // setup
        RegisterPostRequest request = RegisterPostRequestFixtures.createRequest();

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        doThrow(ConflicException.class).when(postService).create(any());

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/post/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isConflict());
    }

    /**
     * Test PUT "/city/"
     *
     * @throws NullPointerException exceptions
     */
    @Test
    public void testRegisterPostThrowNPE() throws Exception {
        // setup
        RegisterPostRequest request = RegisterPostRequestFixtures.createRequest();

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        doThrow(NullPointerException.class).when(postService).create(any());

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/post/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest());
    }

    /**
     * Test PUT "/post/{postId}"
     *
     */
    @Test
    public void testUpdatePost() throws Exception {
        // setup
        RegisterPostRequest request = RegisterPostRequestFixtures.createRequest();

        TblPost tblPost = TblPostFixtures.createPost();
        when(postService.update(anyString(), any())).thenReturn(tblPost);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(put("/post/{postId}", tblPost.getPostId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.post_code", is(tblPost.getPostCode())))
                .andExpect(jsonPath("$.update_show", is(tblPost.getUpdateShow())))
                .andExpect(jsonPath("$.change_reason", is(tblPost.getChangeReason())))
                .andExpect(jsonPath("$.multi_area", is(tblPost.getMultiArea())));
    }

    /**
     * Test PUT "/post/{postId}"
     *
     * @throws IllegalArgumentException exceptions
     */
    @Test
    public void testUpdatePostThrowIAE() throws Exception {
        // setup
        RegisterPostRequest request = RegisterPostRequestFixtures.createRequest();
        TblPost tblPost = TblPostFixtures.createPost();

        doThrow(IllegalArgumentException.class).when(postService).update(anyString(),any());

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(put("/post/{postId}", tblPost.getPostId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())));
    }

    /**
     * Test PUT "/post/{postId}"
     *
     * @throws ConflicException exceptions
     */
    @Test
    public void testUpdatePostThrowCE() throws Exception {
        // setup
        RegisterPostRequest request = RegisterPostRequestFixtures.createRequest();
        TblPost tblPost = TblPostFixtures.createPost();

        doThrow(ConflicException.class).when(postService).update(anyString(),any());

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(put("/post/{postId}", tblPost.getPostId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // verify
                .andExpect(status().isConflict());
    }
}
