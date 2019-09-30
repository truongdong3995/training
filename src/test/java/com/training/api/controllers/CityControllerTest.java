package com.training.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.CityFixtures;
import com.training.api.models.RegisterCityRequest;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.UpdateCityRequest;
import com.training.api.models.fixtures.RegisterCityRequestFixtures;
import com.training.api.models.fixtures.UpdateCityRequestFixtures;
import com.training.api.services.CityService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.AlreadyExistsException;
import com.training.api.utils.exceptions.InvalidModelException;
import javassist.NotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

	@MockBean
	private ApiMessage apiMessage;
	
	
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
		
		when(cityService.searchAddressByPrefectureCode(anyString())).thenReturn(searchPrefectureCodeResponseList);
		
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
		doThrow(IllegalArgumentException.class).when(cityService).searchAddressByPrefectureCode(anyString());
		
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
		doThrow(NotFoundException.class).when(cityService).searchAddressByPrefectureCode(anyString());
		
		// exercise
		mvc.perform(get("/post_offices/prefectures/{prefectures_code}",
				city.getPrefecture().getPrefectureCode()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isNotFound());
	}
	
	/**
	 * Test GET "/citys/"
	 *
	 *
	 */
	@Test()
	public void testGetAll() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		List<City> cityList = new ArrayList<>();
		cityList.add(city);

		when(cityService.findAll()).thenReturn(cityList);

		// exercise
		mvc.perform(get("/citys").contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].code", is(city.getCode())))
			.andExpect(jsonPath("$.data[0].city_kana", is(city.getCityKana())))
			.andExpect(jsonPath("$.data[0].city", is(city.getCityName())));
	}

	/**
	 * Test GET "/citys/{code}"
	 *
	 *
	 */
	@Test
	public void testGetCity() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.findCityByCode(anyString())).thenReturn(Optional.of(city));

		// exercise
		mvc.perform(get("/citys/{code}", city.getCityId()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code", is(city.getCode())))
			.andExpect(jsonPath("$.city_kana", is(city.getCityKana())))
			.andExpect(jsonPath("$.city", is(city.getCityName())));
	}

	/**
	 * Test GET "/citys/{code}"
	 *
	 * @throws IllegalArgumentException exceptions
	 */
	@Test
	public void testGetCityCatchIAE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		doThrow(IllegalArgumentException.class).when(cityService).findCityByCode(anyString());

		// exercise
		mvc.perform(get("/citys/{code}", city.getCityId()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}

	/**
	 * Test GET "/citys/{code}"
	 *
	 *
	 */
	@Test
	public void testGetCityCatchNFE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.findCityByCode(anyString())).thenReturn(Optional.empty());

		// exercise
		mvc.perform(get("/citys/{code}", city.getCode()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isNotFound());
	}

	/**
	 * Test PUT "/citys/"
	 *
	 *
	 */
	@Test
	public void testRegisterCity() throws Exception {
		// setup
		String code = "25562";
		RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest(code);

		City city = request.get();
		when(cityService.create(any(City.class))).thenReturn(city);

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		// exercise
		mvc.perform(put("/citys/")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			// verify
			.andExpect(status().isOk());
	}

	/**
	 * Test PUT "/citys/"
	 *
	 * @throws AlreadyExistsException
	 */
	@Test
	public void testRegisterCityThrowCE() throws Exception {
		// setup
		String code = "25562";
		RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest(code);
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		doThrow(AlreadyExistsException.class).when(cityService).create(any(City.class));

		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/citys/")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isConflict());
	}

	/**
	 * Test POST "/citys/"
	 *
	 * @throws InvalidModelException
	 */
	@Test
	public void testRegisterCityThrowIME() throws Exception {
		// setup
		String code = "25562";
		RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest(code);
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		doThrow(InvalidModelException.class).when(cityService).create(any(City.class));

		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/citys")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				// verify
				.andExpect(status().isBadRequest());
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
		City city = CityFixtures.createCity();
		when(cityService.findCityByCode(anyString())).thenReturn(Optional.of(city));
		when(cityService.update(any(City.class))).thenReturn(city);

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/citys/{code}", city.getCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk());
	}

	/**
	 * Test PUT "/citys/{code}"
	 *
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testUpdateCityThrowIAE() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		City city = CityFixtures.createCity();

		when(cityService.findCityByCode(anyString())).thenReturn(Optional.of(city));
		doThrow(IllegalArgumentException.class).when(cityService).update(any(City.class));

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/citys/{code}", city.getCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}

	/**
	 * Test PUT "/citys/{code}"
	 *
	 * @throws AlreadyExistsException
	 */
	@Test
	public void testUpdateCityThrowCE() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		City city = CityFixtures.createCity();

		when(cityService.findCityByCode(anyString())).thenReturn(Optional.of(city));
		doThrow(AlreadyExistsException.class).when(cityService).update(any(City.class));

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/citys/{code}", city.getCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isConflict());
	}

	/**
	 * Test PUT "/citys/{code}"
	 *
	 * @throws AlreadyExistsException
	 */
	@Test
	public void testUpdateCityThrowIME() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		City city = CityFixtures.createCity();

		when(cityService.findCityByCode(anyString())).thenReturn(Optional.of(city));
		doThrow(InvalidModelException.class).when(cityService).update(any(City.class));

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);

		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/citys/{code}", city.getCode())
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				// verify
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test DELETE "/citys/{code}"
	 *
	 */
	@Test
	public void deleteCity() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.findCityByCode(anyString())).thenReturn(Optional.of(city));
		when(cityService.deleteCity(any(City.class))).thenReturn(city);

		// exercise
		mvc.perform(delete("/citys/{code}", city.getCityId()))
			.andDo(print())
			// verify
			.andExpect(status().isOk());
	}

	/**
	 * Test DELETE "/citys/{code}" throws NotFoundException
	 *
	 */
	@Test
	public void deleteCityThrowsNFE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.findCityByCode(anyString())).thenReturn(Optional.empty());
		// exercise
		mvc.perform(delete("/citys/{code}", city.getCityId()))
			.andDo(print())
			// verify
			.andExpect(status().isNotFound());
	}
}
