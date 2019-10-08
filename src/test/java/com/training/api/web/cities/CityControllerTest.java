package com.training.api.web.cities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.model.cities.City;
import com.training.api.model.cities.CityFixtures;
import com.training.api.model.cities.CityService;
import com.training.api.utils.ApiMessage;
import com.training.api.model.AlreadyExistsException;
import com.training.api.model.InvalidModelException;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CityController}.
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
	 * Test POST "/cities"
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
		mvc.perform(post("/cities")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			// verify
			.andExpect(status().isOk());
	}
	
	/**
	 * Test POST "/cities"
	 *
	 * @throws AlreadyExistsException If City existed
	 */
	@Test
	public void testRegisterCityThrowAEE() throws Exception {
		// setup
		String code = "25562";
		RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest(code);
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		doThrow(AlreadyExistsException.class).when(cityService).create(any(City.class));
		
		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/cities")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isConflict());
	}
	
	/**
	 * Test POST "/cities"
	 *
	 * @throws InvalidModelException If city error
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
		mvc.perform(MockMvcRequestBuilders.post("/cities")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test GET "/cities/{code}"
	 *
	 *
	 */
	@Test
	public void testGetCity() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.find(anyString())).thenReturn(Optional.of(city));
		
		// exercise
		mvc.perform(get("/cities/{code}", city.getCityId()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code", is(city.getCityCode())))
			.andExpect(jsonPath("$.city_kana", is(city.getCityKana())))
			.andExpect(jsonPath("$.city", is(city.getCityName())));
	}
	
	/**
	 * Test GET "/cities/{code}"
	 *
	 * @throws IllegalArgumentException exceptions
	 */
	@Test
	public void testGetCityCatchIAE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		doThrow(IllegalArgumentException.class).when(cityService).find(anyString());
		
		// exercise
		mvc.perform(get("/cities/{code}", city.getCityId()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test GET "/cities/{code}"
	 *
	 *
	 */
	@Test
	public void testGetCityCatchNFE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.find(anyString())).thenReturn(Optional.empty());
		
		// exercise
		mvc.perform(get("/cities/{code}", city.getCityCode()).contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isNotFound());
	}
	
	/**
	 * Test PUT "/cities/{cityId}"
	 *
	 */
	@Test
	public void testUpdateCity() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		City city = CityFixtures.createCity();
		when(cityService.find(anyString())).thenReturn(Optional.of(city));
		when(cityService.update(any(City.class))).thenReturn(city);
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/cities/{code}", city.getCityCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isOk());
	}
	
	/**
	 * Test PUT "/cities/{code}"
	 *
	 * @throws IllegalArgumentException If code must not half size number
	 */
	@Test
	public void testUpdateCityThrowIAE() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		City city = CityFixtures.createCity();
		
		when(cityService.find(anyString())).thenReturn(Optional.of(city));
		doThrow(IllegalArgumentException.class).when(cityService).update(any(City.class));
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/cities/{code}", city.getCityCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test PUT "/cities/{code}"
	 *
	 * @throws AlreadyExistsException If city existed
	 */
	@Test
	public void testUpdateCityThrowAEE() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		City city = CityFixtures.createCity();
		
		when(cityService.find(anyString())).thenReturn(Optional.of(city));
		doThrow(AlreadyExistsException.class).when(cityService).update(any(City.class));
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/cities/{code}", city.getCityCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isConflict());
	}
	
	/**
	 * Test PUT "/cities/{code}"
	 *
	 * @throws InvalidModelException If city validate error
	 */
	@Test
	public void testUpdateCityThrowIME() throws Exception {
		// setup
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		City city = CityFixtures.createCity();
		
		when(cityService.find(anyString())).thenReturn(Optional.of(city));
		doThrow(InvalidModelException.class).when(cityService).update(any(City.class));
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		// exercise
		mvc.perform(MockMvcRequestBuilders.post("/cities/{code}", city.getCityCode())
			.content(content)
			.contentType(MediaType.APPLICATION_JSON))
			// verify
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test DELETE "/cities/{code}"
	 *
	 */
	@Test
	public void deleteCity() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.find(anyString())).thenReturn(Optional.of(city));
		when(cityService.delete(any(City.class))).thenReturn(city);
		
		// exercise
		mvc.perform(delete("/cities/{code}", city.getCityId()))
			.andDo(print())
			// verify
			.andExpect(status().isOk());
	}
	
	/**
	 * Test DELETE "/cities/{code}" throws NotFoundException
	 *
	 */
	@Test
	public void deleteCityThrowsNFE() throws Exception {
		// setup
		City city = CityFixtures.createCity();
		when(cityService.find(anyString())).thenReturn(Optional.empty());
		// exercise
		mvc.perform(delete("/cities/{code}", city.getCityId()))
			.andDo(print())
			// verify
			.andExpect(status().isNotFound());
	}
}
