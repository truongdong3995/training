package com.training.api.controllers;

import com.training.api.models.RegisterCityRequest;
import com.training.api.models.RegisterPostRequest;
import com.training.api.models.fixtures.RegisterCityRequestFixtures;
import com.training.api.models.fixtures.RegisterPostRequestFixtures;
import net.moznion.random.string.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigInteger;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractIntegrationTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	private static final String FORMAT = "%05d";
	
	private static final Random RAND = new Random();
	
	private static final RandomStringGenerator RANDOM_STRING_GENERATOR = new RandomStringGenerator();
	
	
	/**
	 * Create credentials header for API requests.
	 *
	 * @return {@link HttpHeaders}
	 */
	protected static HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	/**
	 * Create new city.
	 *
	 * @return code city.
	 */
	protected String createCity(HttpHeaders headers, String code) {
		// setup
		RegisterCityRequest request = RegisterCityRequestFixtures.creatRequest(code);
		HttpEntity<RegisterCityRequest> entity = new HttpEntity<>(request, headers);
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/cities", HttpMethod.POST, entity, String.class);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		return request.getCode();
	}
	
	/**
	 * Create new post.
	 *
	 * @return postCode post code.
	 */
	protected String createPost(HttpHeaders headers, String postCode) {
		// setup
		RegisterPostRequest request = RegisterPostRequestFixtures.createRequest("0010000");
		HttpEntity<RegisterPostRequest> entity = new HttpEntity<>(request, headers);
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/posts", HttpMethod.POST, entity, String.class);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		return request.getPostCode();
	}
	
	/**
	 * Generate random numeric code.
	 *
	 * @return code
	 */
	protected static String randomCode() {
		return String.format(FORMAT, new BigInteger(64, RAND).longValue());
	}
}
