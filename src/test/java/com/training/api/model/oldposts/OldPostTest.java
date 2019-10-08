package com.training.api.model.oldposts;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link OldPost}.
 *
 */
public class OldPostTest {
	
	JacksonTester<OldPost> json;
	
	private ObjectMapper mapper;
	
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	@Before
	public void setUp() {
		mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		JacksonTester.initFields(this, mapper);
		assert json != null; // required for FindBugs NP_UNWRITTEN_FIELD
	}
	
	@Test
	public void testOK() {
		// setup
		OldPost sut = OldPostFixtures.createOldPost();
		// exercise
		Set<ConstraintViolation<OldPost>> actual = validator.validate(sut);
		// verify
		assertThat(actual).hasSize(0);
	}
	
	@Test
	public void testNotNullConstraints() {
		// setup
		OldPost sut = new OldPost();
		// exercise
		Set<ConstraintViolation<OldPost>> actual = validator.validate(sut);
		// verify
		assertThat(actual).hasSize(1);
	}
	
	@Test
	public void testValueToJson() throws Exception {
		// setup
		OldPost tblOldPost = OldPostFixtures.createOldPost();
		// exercise
		JsonContent<OldPost> actual = json.write(tblOldPost);
		// verify
		assertThat(actual).extractingJsonPathStringValue("@.old_post_code").isEqualTo("001");
	}
	
	@Test
	public void testValueToTree() {
		// setup
		OldPost tblOldPost = OldPostFixtures.createOldPost();
		// exercise: Value to Tree
		JsonNode actualNode = mapper.valueToTree(tblOldPost);
		assertThat(actualNode.path("old_post_code").isTextual()).isTrue();
		assertThat(actualNode.path("old_post_code").textValue()).isEqualTo("001");
	}
}
