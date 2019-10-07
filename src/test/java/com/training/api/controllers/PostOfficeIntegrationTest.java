package com.training.api.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Integration test for PostOffice Command APIs.
 *
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-integrationtest.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostOfficeIntegrationTest extends AbstractIntegrationTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	
	/**
	 * Test GET "/post_offices/prefectures/{prefectureCode}"
	 *
	 */
	@Test
	public void testSearchAddressByPrefectureCode() {
		// setup
		HttpHeaders headers = createHeaders();
		String prefectureCode = "01";
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/post_offices/prefectures/{prefectureCode}", HttpMethod.GET,
						new HttpEntity<>(headers),
						String.class, prefectureCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.data[0].code", is("01102"))
			.assertThat("$.data[0].city", is("札幌市北区"))
			.assertThat("$.data[0].city_kana", is("ｻｯﾎﾟﾛｼｷﾀ"));
	}
	
	/**
	 * Test GET "/post_offices/prefectures/{prefectureCode}" throw NotFoundException
	 *
	 */
	@Test
	public void testSearchAddressByPrefectureCodeThrowNFE() {
		// setup
		HttpHeaders headers = createHeaders();
		String prefectureCode = randomCode(2);
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/post_offices/prefectures/{prefectureCode}", HttpMethod.GET,
						new HttpEntity<>(headers),
						String.class, prefectureCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		with(actual.getBody())
			.assertThat("$.error", is("404"));
	}
	
	/**
	 * Test GET "/post_offices/prefectures/{prefectureCode}" throw IllegalArgumentException
	 *
	 */
	@Test
	public void testSearchAddressByPrefectureCodeThrowIAE() {
		// setup
		HttpHeaders headers = createHeaders();
		String prefectureCode = "TEST1";
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/post_offices/prefectures/{prefectureCode}", HttpMethod.GET,
						new HttpEntity<>(headers),
						String.class, prefectureCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		with(actual.getBody())
			.assertThat("$.error", is("400"));
	}
	
	/**
	 * Test GET "/post_offices/posts/{postCode}"
	 *
	 */
	@Test
	public void testSearchAddressByPostCode() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = "0010000";
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/post_offices/posts/{postCode}", HttpMethod.GET,
						new HttpEntity<>(headers),
						String.class, postCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.data[0].code", is("01102"))
			.assertThat("$.data[0].city", is("札幌市北区"))
			.assertThat("$.data[0].city_kana", is("ｻｯﾎﾟﾛｼｷﾀ"))
			.assertThat("$.data[0].prefecture", is("北海道"))
			.assertThat("$.data[0].prefecture_kana", is("ﾎｯｶｲﾄﾞｳ"))
			.assertThat("$.data[0].prefecture_code", is("01"));
	}
	
	/**
	 * Test GET "/post_offices/posts/{postCode}" throw NotFoundException
	 *
	 */
	@Test
	public void testSearchAddressByPostCodeThrowNFE() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = randomCode(2);
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/post_offices/prefectures/{prefectureCode}", HttpMethod.GET,
						new HttpEntity<>(headers),
						String.class, postCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		with(actual.getBody())
			.assertThat("$.error", is("404"));
	}
	
	/**
	 * Test GET "/post_offices/posts/{postCode}" throw IllegalArgumentException
	 *
	 */
	@Test
	public void testSearchAddressByPostCodeThrowIAE() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = "TEST2";
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/post_offices/prefectures/{prefectureCode}", HttpMethod.GET,
						new HttpEntity<>(headers),
						String.class, postCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		with(actual.getBody())
			.assertThat("$.error", is("400"));
	}
	
}
