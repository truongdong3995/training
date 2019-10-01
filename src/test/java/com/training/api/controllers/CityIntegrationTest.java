package com.training.api.controllers;

import com.training.api.models.RegisterCityRequest;
import com.training.api.models.UpdateCityRequest;
import com.training.api.models.fixtures.RegisterCityRequestFixtures;
import com.training.api.models.fixtures.UpdateCityRequestFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
public class CityIntegrationTest extends AbstractIntegrationTest {
	
	@Autowired
	TestRestTemplate restTemplate;

	
	/**
	 * Test GET /cities/{code}
	 *
	 */
	@Test
	public void testGetCity() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = createCity(headers, randomCode());
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/cities/{code}", HttpMethod.GET, new HttpEntity<>(headers),
						String.class, code);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.code", is(code));
	}
	
	/**
	 * Test GET /cities/{code} throws NotFoundException
	 *
	 */
	@Test
	public void testGetCityThrowsNFE() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = randomCode();
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/cities/{code}", HttpMethod.GET, new HttpEntity<>(headers),
						String.class, code);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		with(actual.getBody())
			.assertThat("$.error", is("404"))
			.assertThat("$.error_description", is("Search not found result"));
	}
	
	/**
	 * Test POST /cities
	 *
	 */
	@Test
	public void testPostArea() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = randomCode();
		RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest(code);
		HttpEntity<RegisterCityRequest> areaRequestEntity = new HttpEntity<>(request, headers);
		// exercise
		ResponseEntity<String> actual = restTemplate
			.exchange("/cities", HttpMethod.POST, areaRequestEntity, String.class);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.code ", is(request.getCode()))
			.assertThat("$.city_kana", is(request.getCityKana()))
			.assertThat("$.city", is(request.getCity()));
	}
	
	/**
	 * Test POST /cities throws AlreadyExistsException
	 *
	 */
	@Test
	public void testPostCityThrowsCE() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = createCity(headers, randomCode());
		RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest(code);
		HttpEntity<RegisterCityRequest> areaRequestEntity = new HttpEntity<>(request, headers);
		// exercise
		
		ResponseEntity<String> actual = restTemplate
			.exchange("/cities", HttpMethod.POST, areaRequestEntity, String.class);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		with(actual.getBody())
			.assertThat("$.error", is("409"))
			.assertThat("$.error_description", is("City has been exist"));
	}
	
	/**
	 * Test DELETE /cities/{code}
	 *
	 */
	@Test
	public void testDeleteCity() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = createCity(headers, randomCode());
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/cities/{code}", HttpMethod.DELETE, new HttpEntity<>(headers),
						String.class, code);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.code", is(code));
		
		ResponseEntity<String> getForVerification =
				restTemplate.exchange("/cities/{code}", HttpMethod.GET, new HttpEntity<>(headers), String.class,
						code); // call GET /cities/{code}
		assertThat(getForVerification.getStatusCode()).isIn(HttpStatus.NOT_FOUND, HttpStatus.GONE);
	}
	
	/**
	 * Test POST /areas/{area_code}
	 *
	 */
	@Test
	public void testUpdateArea() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = createCity(headers, randomCode());
		UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/cities/{code}", HttpMethod.POST, new HttpEntity<>(request, headers),
						String.class, code);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.city_kana", is(request.getCityKana()))
			.assertThat("$.city", is(request.getCity()));
	}
	
	/**
	 * Test POST /cities/{code} throws AlreadyExistsException
	 *
	 *
	 */
	@Test
	public void testUpdateAreaThrowsCE() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = createCity(headers, randomCode());
		RegisterCityRequest requestRegister = RegisterCityRequestFixtures.creatRequest(code);
		UpdateCityRequest requestUpdate = UpdateCityRequestFixtures.creatRequest();
		requestUpdate.setCityKana(requestRegister.getCityKana());
		requestUpdate.setCity(requestRegister.getCity());
		requestUpdate.setPrefecture(requestRegister.getPrefecture());
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/cities/{code}", HttpMethod.POST, new HttpEntity<>(requestUpdate, headers),
						String.class, code);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		with(actual.getBody())
			.assertThat("$.error", is("409"))
			.assertThat("$.error_description", is("City has been exist"));
	}
}
