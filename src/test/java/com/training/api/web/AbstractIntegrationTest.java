package com.training.api.web;

import com.training.api.web.cities.RegisterCityRequest;
import com.training.api.web.posts.RegisterPostRequest;
import com.training.api.web.cities.RegisterCityRequestFixtures;
import com.training.api.web.posts.RegisterPostRequestFixtures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigInteger;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract implementation for integration test.
 *
 */
public class AbstractIntegrationTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	private static final String FORMAT = "%05d";
	
	private static final Random RAND = new Random();
	
	
	/**
	 * Generate random numeric code with max given length.
	 *
	 * @param maxLen max length
	 * @return code
	 */
	protected static String randomCode(int maxLen) {
		String randomCode = randomCode();
		if (randomCode.length() > maxLen) {
			randomCode = randomCode.substring(randomCode.length() - maxLen);
		}
		return randomCode;
	}
	
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
		request.setCityKana(randomCode(10));
		HttpEntity<RegisterCityRequest> entity = new HttpEntity<>(request, headers);
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/cities", HttpMethod.POST, entity, String.class);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		return request.getCityCode();
	}
	
	/**
	 * Create new post.
	 *
	 * @return postCode post code.
	 */
	protected String createPost(HttpHeaders headers, String postCode) {
		// setup
		RegisterPostRequest request = RegisterPostRequestFixtures.createRequest(postCode);
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
		return String.format(FORMAT, new BigInteger(64, RAND).intValue());
	}
}
