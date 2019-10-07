package com.training.api.controllers;

import com.training.api.entitys.Area;
import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.AreaFixtures;
import com.training.api.entitys.fixtures.CityFixtures;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.services.PostOfficeService;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link PostOfficeControllerTest}
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostOfficeController.class)
public class PostOfficeControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PostOfficeService postOfficeService;
	
	
	/**
	 * Test GET "/post_offices/prefectures/{prefecturesCode}"
	 *
	 *
	 */
	@Test
	public void testSearchAddressByPrefectureCode() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		SearchPrefectureCodeResponse searchPrefectureCodeResponse = new SearchPrefectureCodeResponse(city);
		List<SearchPrefectureCodeResponse> searchPrefectureCodeResponseList = new ArrayList<>();
		searchPrefectureCodeResponseList.add(searchPrefectureCodeResponse);
		
		when(postOfficeService.searchAddressByPrefectureCode(anyString())).thenReturn(searchPrefectureCodeResponseList);
		
		// exercise
		mvc.perform(get("/post_offices/prefectures/{prefecturesCode}",
				city.getPrefecture().getPrefectureCode())
					.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].code", is(city.getCode())))
			.andExpect(jsonPath("$.data[0].city", is(city.getCityName())))
			.andExpect(jsonPath("$.data[0].city_kana", is(city.getCityKana())))
			.andExpect(jsonPath("$.data[0].prefecture_code", is(city.getPrefecture().getPrefectureCode())));
	}
	
	/**
	 * Test GET "/post_offices/prefectures/{prefecturesCode}"
	 *
	 * @throws IllegalArgumentException exceptions
	 */
	@Test
	public void testSearchAddressByPrefectureCodeThrowIAE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		doThrow(IllegalArgumentException.class).when(postOfficeService).searchAddressByPrefectureCode(anyString());
		
		// exercise
		mvc.perform(get("/post_offices/prefectures/{prefectures_code}",
				city.getPrefecture().getPrefectureCode()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test GET "/post_offices/prefectures/{prefecturesCode}"
	 *
	 * @throws NotFoundException exceptions
	 */
	@Test
	public void searchByPrefectureCodeThrowNFE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		doThrow(NotFoundException.class).when(postOfficeService).searchAddressByPrefectureCode(anyString());
		
		// exercise
		mvc.perform(get("/post_offices/prefectures/{prefectures_code}",
				city.getPrefecture().getPrefectureCode()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isNotFound());
	}
	
	/**
	 * Test GET "post_offices/posts/{postCode}"
	 *
	 */
	@Test
	public void testSearchAddressByPostCode() throws Exception {
		// setup
		Area tblArea = AreaFixtures.createArea();
		SearchPostCodeResponse response = new SearchPostCodeResponse(tblArea);
		List<SearchPostCodeResponse> responseList = new ArrayList<>();
		responseList.add(response);
		
		when(postOfficeService.searchAddressByPostCode(anyString())).thenReturn(responseList);
		
		// exercise
		mvc.perform(get("/post_offices/posts/{postCode}",
				tblArea.getPost().getPostCode())
					.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].area", is(tblArea.getArea())))
			.andExpect(jsonPath("$.data[0].area_kana", is(tblArea.getAreaKana())))
			.andExpect(jsonPath("$.data[0].city", is(tblArea.getCity().getCityName())))
			.andExpect(jsonPath("$.data[0].city_kana", is(tblArea.getCity().getCityKana())));
	}
	
	/**
	 * Test GET "post_offices/posts/{postCode}"
	 *
	 * @throws IllegalArgumentException exceptions
	 */
	@Test
	public void testSearchAddressByPostCodeThrowIAE() throws Exception {
		// setup
		Area tblArea = AreaFixtures.createArea();
		doThrow(IllegalArgumentException.class).when(postOfficeService).searchAddressByPostCode(anyString());
		
		// exercise
		mvc.perform(get("/post_offices/posts/{postCode}",
				tblArea.getPost().getPostCode())
					.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test GET "post_offices/posts/{postCode}"
	 *
	 * @throws NotFoundException exceptions
	 */
	@Test
	public void testSearchAddressByPostCodeThrowNFE() throws Exception {
		// setup
		Area tblArea = AreaFixtures.createArea();
		doThrow(NotFoundException.class).when(postOfficeService).searchAddressByPostCode(anyString());
		
		// exercise
		mvc.perform(get("/posts_offices/posts/{postCode}",
				tblArea.getPost().getPostCode())
					.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isNotFound());
	}
}
