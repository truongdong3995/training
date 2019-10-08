package com.training.api.model.prefectures;

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
 * Test for {@link Prefecture}.
 *
 */
public class PrefectureTest {
	
	JacksonTester<Prefecture> json;
	
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
		Prefecture sut = PrefectureFixtures.createPrefecture();
		// exercise
		Set<ConstraintViolation<Prefecture>> actual = validator.validate(sut);
		// verify
		assertThat(actual).hasSize(0);
	}
	
	@Test
	public void testNotNullConstraints() {
		// setup
		Prefecture sut = new Prefecture();
		// exercise
		Set<ConstraintViolation<Prefecture>> actual = validator.validate(sut);
		// verify
		assertThat(actual).hasSize(3);
	}
	
	@Test
	public void testValueToJson() throws Exception {
		// setup
		Prefecture tblPrefecture = PrefectureFixtures.createPrefecture();
		// exercise
		JsonContent<Prefecture> actual = json.write(tblPrefecture);
		// verify
		assertThat(actual).extractingJsonPathStringValue("@.prefecture_kana").isEqualTo("ﾎｯｶｲﾄﾞｳ");
		assertThat(actual).extractingJsonPathStringValue("@.prefecture").isEqualTo("北海道");
		assertThat(actual).extractingJsonPathStringValue("@.prefecture_code").isEqualTo("01");
	}
	
	@Test
	public void testValueToTree() {
		// setup
		Prefecture tblPrefecture = PrefectureFixtures.createPrefecture();
		// exercise: Value to Tree
		JsonNode actualNode = mapper.valueToTree(tblPrefecture);
		assertThat(actualNode.path("prefecture_kana").isTextual()).isTrue();
		assertThat(actualNode.path("prefecture_kana").textValue()).isEqualTo("ﾎｯｶｲﾄﾞｳ");
		assertThat(actualNode.path("prefecture").isTextual()).isTrue();
		assertThat(actualNode.path("prefecture").textValue()).isEqualTo("北海道");
		assertThat(actualNode.path("prefecture_code").isTextual()).isTrue();
		assertThat(actualNode.path("prefecture_code").textValue()).isEqualTo("01");
	}
}
