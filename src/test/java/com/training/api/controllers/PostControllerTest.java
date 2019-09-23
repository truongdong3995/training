package com.training.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPost;
import com.training.api.services.PostService;
import static org.hamcrest.CoreMatchers.is;

import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.NoExistResourcesException;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    private TblPost tblPost;

    @Before
    public void setUp(){
        tblPost = new TblPost(382013,"9998227",0,0,0);
    }

    @Test
    public void findPostById() throws Exception {
        // setup
        when(postService.findPostById(anyInt())).thenReturn(tblPost);

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post/4").contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId", is(tblPost.getPostId())))
                .andExpect(jsonPath("$.postCode", is(tblPost.getPostCode())))
        ;
    }

    @Test
    public void findPostByIdError404() throws Exception {
        // setup
        doThrow(NoExistResourcesException.class).when(postService).findPostById(anyInt());
        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post/4").contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())))
        ;
    }

    @Test
    public void registerPost() throws Exception {
        // setup
        when(postService.create(any(TblPost.class))).thenReturn(tblPost);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblPost);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/post/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId", is(tblPost.getPostId())))
                .andExpect(jsonPath("$.postCode", is(tblPost.getPostCode())))
        ;
    }

    @Test
    public void registerPostError400() throws Exception {
        // setup
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblPost);

        doThrow(ConflicException.class).when(postService).create(any(TblPost.class));

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/post/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())))
        ;
    }

    @Test
    public void updateCity() throws Exception {
        // setup
        TblPost tblPostRequestBody = new TblPost(382013,"9999999",0,0,1);
        when(postService.update(anyInt(), any(TblPost.class))).thenReturn(tblPostRequestBody);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblPostRequestBody);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/post/{cityId}",tblPostRequestBody.getPostId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId", is(tblPostRequestBody.getPostId())))
                .andExpect(jsonPath("$.postCode", is(tblPostRequestBody.getPostCode())))
                .andExpect(jsonPath("$.multiArea", is(tblPostRequestBody.getMultiArea())))
        ;
    }

    @Test
    public void updateCityError400() throws Exception {
        // setup
        TblPost tblPostRequestBody = new TblPost(382013,"9999999",0,0,1);
        doThrow(NoExistResourcesException.class).when(postService).update(anyInt(),any(TblPost.class));

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblPostRequestBody);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/post/{cityId}",tblPostRequestBody.getPostId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())))
        ;
    }
}
