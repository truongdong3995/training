package com.training.api.controllers;

import com.training.api.models.RegisterPostRequest;
import com.training.api.models.UpdatePostRequest;
import com.training.api.models.fixtures.RegisterPostRequestFixtures;
import com.training.api.models.fixtures.UpdatePostRequestFixtures;
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
@ActiveProfiles({
		"integrationtest"
})
public class PostIntegrationTest extends AbstractIntegrationTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	
	/**
	 * Test GET /posts/{postCode}
	 *
	 */
	@Test
	public void testGetPost() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = createPost(headers, randomCode());
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/posts/{postCode}", HttpMethod.GET, new HttpEntity<>(headers),
						String.class, postCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.postCode", is(postCode));
	}
	
	/**
	 * Test GET /posts/{postCode} throws NotFoundException
	 *
	 */
	@Test
	public void testGetPostThrowsNFE() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = randomCode();
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/posts/{postCode}", HttpMethod.GET, new HttpEntity<>(headers),
						String.class, postCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		with(actual.getBody())
			.assertThat("$.error", is("404"))
			.assertThat("$.error_description", is("Search not found result"));
	}
	
	/**
	 * Test POST /posts
	 *
	 */
	@Test
	public void testRegisterPost() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = randomCode();
		RegisterPostRequest request = RegisterPostRequestFixtures.createRequest(postCode);
		HttpEntity<RegisterPostRequest> areaRequestEntity = new HttpEntity<>(request, headers);
		// exercise
		ResponseEntity<String> actual = restTemplate
			.exchange("/posts", HttpMethod.POST, areaRequestEntity, String.class);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.post_code ", is(request.getPostCode()))
			.assertThat("$.update_show", is(request.getUpdateShow()))
			.assertThat("$.change_reason", is(request.getChangeReason()));
	}
	
	/**
	 * Test POST /posts throws AlreadyExistsException
	 *
	 */
	@Test
	public void testRegisterPostThrowsCE() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = createPost(headers, randomCode());
		RegisterPostRequest request = RegisterPostRequestFixtures.createRequest(postCode);
		HttpEntity<RegisterPostRequest> areaRequestEntity = new HttpEntity<>(request, headers);
		// exercise
		
		ResponseEntity<String> actual = restTemplate
			.exchange("/posts", HttpMethod.POST, areaRequestEntity, String.class);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		with(actual.getBody())
			.assertThat("$.error", is("409"))
			.assertThat("$.error_description", is("Post has been exist"));
	}
	
	/**
	 * Test DELETE /posts/{postCode}
	 *
	 */
	@Test
	public void testDeletePost() {
		// setup
		HttpHeaders headers = createHeaders();
		String postCode = createPost(headers, randomCode());
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/posts/{postCode}", HttpMethod.DELETE, new HttpEntity<>(headers),
						String.class, postCode);
		
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.code", is(postCode));
		
		ResponseEntity<String> getForVerification =
				restTemplate.exchange("/posts/{postCode}", HttpMethod.GET, new HttpEntity<>(headers), String.class,
						postCode); // call GET /posts/{postCode}
		assertThat(getForVerification.getStatusCode()).isIn(HttpStatus.NOT_FOUND, HttpStatus.GONE);
	}
	
	/**
	 * Test POST /posts/{postCode}
	 *
	 */
	@Test
	public void testUpdatePost() {
		// setup
		HttpHeaders headers = createHeaders();
		String code = createPost(headers, randomCode());
		UpdatePostRequest request = UpdatePostRequestFixtures.createRequest();
		// exercise
		ResponseEntity<String> actual =
				restTemplate.exchange("/posts/{postCode}", HttpMethod.POST, new HttpEntity<>(request, headers),
						String.class, code);
		// verify
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
		with(actual.getBody())
			.assertThat("$.update_show", is(request.getUpdateShow()))
			.assertThat("$.change_reason", is(request.getChangeReason()))
			.assertThat("$.multi_area", is(request.getMultiArea()));
	}
}
