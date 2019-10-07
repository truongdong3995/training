package com.training.api.entitys;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.fixtures.AreaFixtures;
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
 * Test for {@link Area}
 *
 */
public class AreaTest {
	
	JacksonTester<Area> json;
	
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
		Area sut = AreaFixtures.createArea();
		// exercise
		Set<ConstraintViolation<Area>> actual = validator.validate(sut);
		// verify
		assertThat(actual).hasSize(0);
	}
	
	@Test
	public void testNotNullConstraints() {
		// setup
		Area sut = new Area();
		// exercise
		Set<ConstraintViolation<Area>> actual = validator.validate(sut);
		// verify
		assertThat(actual).hasSize(2);
	}
	
	@Test
	public void testValueToJson() throws Exception {
		// setup
		Area tblArea = AreaFixtures.createArea();
		// exercise
		JsonContent<Area> actual = json.write(tblArea);
		// verify
		assertThat(actual).extractingJsonPathStringValue("@.city.tblPrefecture.prefecture_kana").isEqualTo("ﾎｯｶｲﾄﾞｳ");
		assertThat(actual).extractingJsonPathStringValue("@.city.tblPrefecture.prefecture").isEqualTo("北海道");
		assertThat(actual).extractingJsonPathStringValue("@.city.tblPrefecture.prefecture_code").isEqualTo("01");
		assertThat(actual).extractingJsonPathStringValue("@.city.code").isEqualTo("01102");
		assertThat(actual).extractingJsonPathStringValue("@.city.city_kana").isEqualTo("ｻｯﾎﾟﾛｼｷﾀ");
		assertThat(actual).extractingJsonPathStringValue("@.city.city").isEqualTo("札幌市北区");
		assertThat(actual).extractingJsonPathStringValue("@.tblPost.post_code").isEqualTo("0010000");
		assertThat(actual).extractingJsonPathNumberValue("@.tblPost.update_show").isEqualTo(0);
		assertThat(actual).extractingJsonPathNumberValue("@.tblPost.change_reason").isEqualTo(0);
		assertThat(actual).extractingJsonPathNumberValue("@.tblPost.multi_area").isEqualTo(0);
		assertThat(actual).extractingJsonPathStringValue("@.tblOldPost.old_post_code").isEqualTo("001");
		assertThat(actual).extractingJsonPathStringValue("@.area_kana").isEqualTo("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ");
		assertThat(actual).extractingJsonPathStringValue("@.area").isEqualTo("以下に掲載がない場合");
		assertThat(actual).extractingJsonPathNumberValue("@.chome_area").isEqualTo(0);
		assertThat(actual).extractingJsonPathNumberValue("@.koaza_area").isEqualTo(0);
		assertThat(actual).extractingJsonPathNumberValue("@.multi_post_area").isEqualTo(0);
	}
	
	@Test
	public void testValueToTree() {
		// setup
		Area tblArea = AreaFixtures.createArea();
		// exercise: Value to Tree
		JsonNode actualNode = mapper.valueToTree(tblArea);
		assertThat(actualNode.path("city").path("tblPrefecture").path("prefecture_kana").isTextual()).isTrue();
		assertThat(actualNode.path("city").path("tblPrefecture").path("prefecture_kana").textValue())
			.isEqualTo("ﾎｯｶｲﾄﾞｳ");
		assertThat(actualNode.path("city").path("tblPrefecture").path("prefecture").isTextual()).isTrue();
		assertThat(actualNode.path("city").path("tblPrefecture").path("prefecture").textValue()).isEqualTo("北海道");
		assertThat(actualNode.path("city").path("tblPrefecture").path("prefecture_code").isTextual()).isTrue();
		assertThat(actualNode.path("city").path("tblPrefecture").path("prefecture_code").textValue()).isEqualTo("01");
		assertThat(actualNode.path("city").path("code").isTextual()).isTrue();
		assertThat(actualNode.path("city").path("code").textValue()).isEqualTo("01102");
		assertThat(actualNode.path("city").path("city_kana").isTextual()).isTrue();
		assertThat(actualNode.path("city").path("city_kana").textValue()).isEqualTo("ｻｯﾎﾟﾛｼｷﾀ");
		assertThat(actualNode.path("city").path("city").isTextual()).isTrue();
		assertThat(actualNode.path("city").path("city").textValue()).isEqualTo("札幌市北区");
		assertThat(actualNode.path("tblPost").path("post_code").isTextual()).isTrue();
		assertThat(actualNode.path("tblPost").path("post_code").textValue()).isEqualTo("0010000");
		assertThat(actualNode.path("tblPost").path("post_code").isTextual()).isTrue();
		assertThat(actualNode.path("tblOldPost").path("old_post_code").textValue()).isEqualTo("001");
		assertThat(actualNode.path("area_kana").isTextual()).isTrue();
		assertThat(actualNode.path("area_kana").textValue()).isEqualTo("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ");
		assertThat(actualNode.path("area").isTextual()).isTrue();
		assertThat(actualNode.path("area").textValue()).isEqualTo("以下に掲載がない場合");
	}
}
