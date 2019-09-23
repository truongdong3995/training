package com.training.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPrefecture;
import com.training.api.services.CityService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.InvalidInputException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService cityService;

    private TblCity tblCity;

    @Before
    public void setUp(){
        tblCity = new TblCity(8190,"09201","ｳﾂﾉﾐﾔｼ","宇都宮市",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));
    }

    @Test
    public void searchByPrefectureCode() throws Exception {
        // setup
        List<TblCity> tblCityList = new ArrayList<>();
        tblCityList.add(tblCity);

        when(cityService.searchCityByPrefectureCode(any())).thenReturn(tblCityList);

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post_offices/prefectures/{prefecturesCode}",
                tblCity.getTblPrefecture().getPrefectureCode())
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].code", is(tblCity.getCode())))
                .andExpect(jsonPath("$.data[0].city", is(tblCity.getCity())))
                .andExpect(jsonPath("$.data[0].city_kana", is(tblCity.getCityKana())))
                .andExpect(jsonPath("$.data[0].prefecture_code", is(tblCity.getTblPrefecture().getPrefectureCode())))
        ;
    }

    @Test
    public void searchByPrefectureCodeError400() throws Exception {
        // setup
        doThrow(InvalidInputException.class).when(cityService).searchCityByPrefectureCode(anyString());

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post_offices/prefectures/{prefectures_code}",
                tblCity.getTblPrefecture().getPrefectureCode()).
                contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())))
        ;
    }

    @Test
    public void searchByPrefectureCodeError404() throws Exception {
        // setup
        doThrow(NoExistResourcesException.class).when(cityService).searchCityByPrefectureCode(anyString());

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/post_offices/prefectures/{prefectures_code}",
                tblCity.getTblPrefecture().getPrefectureCode()).
                contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())))
        ;
    }

    @Test
    public void getAll() throws Exception {
        // setup
        List<TblCity> tblCityList = new ArrayList<>();
        tblCityList.add(tblCity);

        when(cityService.findAll()).thenReturn(tblCityList);

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/city/").contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].cityId", is(tblCity.getCityId())))
                .andExpect(jsonPath("$.data[0].code", is(tblCity.getCode())))
                .andExpect(jsonPath("$.data[0].cityKana", is(tblCity.getCityKana())))
                .andExpect(jsonPath("$.data[0].city", is(tblCity.getCity())))
        ;
    }

    @Test
    public void findCityById() throws Exception {
        // setup
        when(cityService.findCityById(anyInt())).thenReturn(tblCity);

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/city/{cityId}",
                tblCity.getCityId()).contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityId", is(tblCity.getCityId())))
                .andExpect(jsonPath("$.code", is(tblCity.getCode())))
                .andExpect(jsonPath("$.cityKana", is(tblCity.getCityKana())))
                .andExpect(jsonPath("$.city", is(tblCity.getCity())))
        ;
    }

    @Test
    public void testFindCityByIdError404() throws Exception {
        // setup
        doThrow(NoExistResourcesException.class).when(cityService).findCityById(anyInt());

        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/city/8189").contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())))
        ;
    }

    @Test
    public void registerCity() throws Exception {
        // setup
        when(cityService.create(any(TblCity.class))).thenReturn(tblCity);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblCity);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/",tblCity)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityId", is(tblCity.getCityId())))
                .andExpect(jsonPath("$.code", is(tblCity.getCode())))
                .andExpect(jsonPath("$.cityKana", is(tblCity.getCityKana())))
                .andExpect(jsonPath("$.city", is(tblCity.getCity())))
        ;
    }

    @Test
    public void registerCityError400() throws Exception {
        // setup
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblCity);

        doThrow(ConflicException.class).when(cityService).create(any(TblCity.class));

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/")
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
        TblCity tblCityRequestBody = new TblCity(8652,"09201","ｳﾂﾉﾐﾔｼupdate","宇都宮市update",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));
        when(cityService.update(anyInt() ,any(TblCity.class))).thenReturn(tblCityRequestBody);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblCityRequestBody);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/{cityId}",tblCityRequestBody.getCityId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityId", is(tblCityRequestBody.getCityId())))
                .andExpect(jsonPath("$.code", is(tblCityRequestBody.getCode())))
                .andExpect(jsonPath("$.cityKana", is(tblCityRequestBody.getCityKana())))
                .andExpect(jsonPath("$.city", is(tblCityRequestBody.getCity())))
        ;
    }

    @Test
    public void updateCityError404() throws Exception {
        // setup
        TblCity tblCityRequestBody = new TblCity(8652,"09201","ｳﾂﾉﾐﾔｼupdate","宇都宮市update",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));

        doThrow(NoExistResourcesException.class).when(cityService).update(anyInt(),any(TblCity.class));

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(tblCityRequestBody);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/{cityId}",tblCityRequestBody.getCityId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())))
        ;
    }
}
