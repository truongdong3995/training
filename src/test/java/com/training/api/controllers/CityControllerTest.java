package com.training.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPrefecture;
import com.training.api.entitys.fixtures.TblAreaFixtures;
import com.training.api.entitys.fixtures.TblCityFixtures;
import com.training.api.models.RegisterCityRequest;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.UpdateCityRequest;
import com.training.api.models.fixtures.RegisterCityRequestFixtures;
import com.training.api.models.fixtures.UpdateCityRequestFixtures;
import com.training.api.services.CityService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.InvalidInputException;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CityController}
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService cityService;

    /**
     * Test GET "/post_offices/prefectures/{prefecturesCode}"
     *
     *
     */
    @Test
    public void testSearchAddressByPrefectureCode() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        SearchPrefectureCodeResponse searchPrefectureCodeResponse = new SearchPrefectureCodeResponse(tblCity);
        List<SearchPrefectureCodeResponse> searchPrefectureCodeResponseList = new ArrayList<>();
        searchPrefectureCodeResponseList.add(searchPrefectureCodeResponse);

        when(cityService.searchAddressByPrefectureCode(anyString())).thenReturn(searchPrefectureCodeResponseList);

        // exercise
        mvc.perform(get("/post_offices/prefectures/{prefecturesCode}",
                tblCity.getTblPrefecture().getPrefectureCode())
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].code", is(tblCity.getCode())))
                .andExpect(jsonPath("$.data[0].city", is(tblCity.getCity())))
                .andExpect(jsonPath("$.data[0].city_kana", is(tblCity.getCityKana())))
                .andExpect(jsonPath("$.data[0].prefecture_code", is(tblCity.getTblPrefecture().getPrefectureCode())));
    }

    /**
     * Test GET "/post_offices/prefectures/{prefecturesCode}"
     *
     * @throws IllegalArgumentException exceptions
     */
    @Test
    public void testSearchAddressByPrefectureCodeThrowIAE() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        doThrow(IllegalArgumentException.class).when(cityService).searchAddressByPrefectureCode(anyString());

        // exercise
        mvc.perform(get("/post_offices/prefectures/{prefectures_code}",
                tblCity.getTblPrefecture().getPrefectureCode()).
                contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())));
    }

    /**
     * Test GET "/post_offices/prefectures/{prefecturesCode}"
     *
     * @throws NotFoundException exceptions
     */
    @Test
    public void searchByPrefectureCodeThrowNFE() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        doThrow(NotFoundException.class).when(cityService).searchAddressByPrefectureCode(anyString());

        // exercise
        mvc.perform(get("/post_offices/prefectures/{prefectures_code}",
                tblCity.getTblPrefecture().getPrefectureCode()).
                contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", CoreMatchers.is(ApiMessage.error404().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error404().getErrorDescription())));
    }

    /**
     * Test GET "/city/"
     *
     *
     */
    @Test()
    public void testGetAll() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        List<TblCity> tblCityList = new ArrayList<>();
        tblCityList.add(tblCity);

        when(cityService.findAll()).thenReturn(tblCityList);

        // exercise
        mvc.perform(get("/city/").contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].code", is(tblCity.getCode())))
                .andExpect(jsonPath("$.data[0].city_kana", is(tblCity.getCityKana())))
                .andExpect(jsonPath("$.data[0].city", is(tblCity.getCity())));
    }

    /**
     * Test GET "/city/{cityId}"
     *
     *
     */
    @Test
    public void testGetCity() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        when(cityService.findCityById(anyString())).thenReturn(Optional.of(tblCity));

        // exercise
        mvc.perform(get("/city/{cityId}", tblCity.getCityId()).contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(tblCity.getCode())))
                .andExpect(jsonPath("$.city_kana", is(tblCity.getCityKana())))
                .andExpect(jsonPath("$.city", is(tblCity.getCity())));
    }

    /**
     * Test GET "/city/{cityId}"
     *
     * @throws IllegalArgumentException exceptions
     */
    @Test
    public void testGetCityCatchIAE() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        doThrow(IllegalArgumentException.class).when(cityService).findCityById(anyString());

        // exercise
        mvc.perform(get("/city/{cityID}", tblCity.getCityId()).contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest());
    }

    /**
     * Test GET "/city/{cityId}"
     *
     *
     */
    @Test
    public void testGetCityCatchNFE() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        when(cityService.findCityById(anyString())).thenReturn(Optional.empty());

        // exercise
        mvc.perform(get("/city/{cityID}", tblCity.getCityId()).contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isNotFound());
    }

    /**
     * Test PUT "/city/"
     *
     *
     */
    @Test
    public void testRegisterCity() throws Exception {
        // setup
        RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest();

        TblCity tblCity = request.get();
        when(cityService.create(any(TblCity.class))).thenReturn(tblCity);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(put("/city/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // verify
                .andExpect(status().isOk());
    }

    /**
     * Test PUT "/city/"
     *
     * @throws ConflicException
     */
    @Test
    public void testRegisterCityThrowCE() throws Exception {
        // setup
        RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        doThrow(ConflicException.class).when(cityService).create(any(TblCity.class));

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isConflict())
        ;
    }

    /**
     * Test PUT "/city/"
     *
     * @throws NullPointerException
     */
    @Test
    public void testRegisterCityThrowNPE() throws Exception {
        // setup
        RegisterCityRequest request = null;
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        doThrow(NullPointerException.class).when(cityService).create(any(TblCity.class));

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
        ;
    }

    /**
     * Test PUT "/city/{cityId}"
     *
     *
     */
    @Test
    public void testUpdateCity() throws Exception {
        // setup
        UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
        TblCity tblCity = TblCityFixtures.createCity();
        when(cityService.update(anyString(), any())).thenReturn(tblCity);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/{cityId}",tblCity.getCityId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isOk());
    }

    /**
     * Test PUT "/city/{cityId}"
     *
     * @throws IllegalArgumentException
     */
    @Test
    public void testUpdateCityThrowIAE() throws Exception {
        // setup
        UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
        TblCity tblCity = TblCityFixtures.createCity();

        doThrow(IllegalArgumentException.class).when(cityService).update(anyString(),any());

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/{cityId}",tblCity.getCityId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ApiMessage.error400().getError())))
                .andExpect(jsonPath("$.error_description", is(ApiMessage.error400().getErrorDescription())));
    }

    /**
     * Test PUT "/city/{cityId}"
     *
     * @throws ConflicException
     */
    @Test
    public void testUpdateCityThrowCE() throws Exception {
        // setup
        UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
        TblCity tblCity = TblCityFixtures.createCity();

        doThrow(ConflicException.class).when(cityService).update(anyString(),any());

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        // exercise
        mvc.perform(MockMvcRequestBuilders.put("/city/{cityId}",tblCity.getCityId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                // verify
                .andExpect(status().isConflict());
    }
}
