package com.training.api.controllers;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblOldPost;
import com.training.api.entitys.TblPost;
import com.training.api.entitys.TblPrefecture;
import com.training.api.services.AreaService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.InvalidInputException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import org.hamcrest.CoreMatchers;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(AreaController.class)
public class AreaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AreaService areaService;

    @Test
    public void testSearchByPostCode1() throws Exception {
        // setup
        TblArea tblArea = new TblArea(102854, "ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ", "以下に掲載がない場合",
                new TblCity(8190, "01102", "ｻｯﾎﾟﾛｼｷﾀ", "札幌市北区",
                        new TblPrefecture(255, "ﾎｯｶｲﾄﾞｳ", "北海道", "01")), 0, 0, 0,
                new TblPost(262141, "0010000", 0, 0, 0), new TblOldPost(8192, "001"));

        ArrayList<TblArea> tblAreaList = new ArrayList<>();
        tblAreaList.add(tblArea);

        when(areaService.searchAreaByPostCode(any())).thenReturn(tblAreaList);

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post_offices/post/001000")
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].code", CoreMatchers.is(tblArea.getTblCity().getCode())))
                .andExpect(jsonPath("$.data[0].post_code", is(tblArea.getTblPost().getPostCode())))
                .andExpect(jsonPath("$.data[0].area_kana", is(tblArea.getAreaKana())))
        ;
    }

    @Test
    public void testSearchByPostCodeError400() throws Exception {
        // setup
        doThrow(InvalidInputException.class).when(areaService).searchAreaByPostCode(anyString());

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post_offices/post/001000")
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())))
        ;
    }

    @Test
    public void testSearchByPostCodeError404() throws Exception {
        // setup
        doThrow(NoExistResourcesException.class).when(areaService).searchAreaByPostCode(anyString());

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post_offices/post/0010000")
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())))
        ;
    }
}
