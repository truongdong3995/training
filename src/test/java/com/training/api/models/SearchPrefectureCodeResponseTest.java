package com.training.api.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.CityFixtures;
import com.training.api.models.fixtures.SearchPrefectureCodeResponseFixtures;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link SearchPrefectureCodeResponse}
 *
 */
public class SearchPrefectureCodeResponseTest {
	
	JacksonTester<SearchPrefectureCodeResponse> json;
	
	private ObjectMapper mapper;
	
	
	@Before
	public void setUp() {
		mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		JacksonTester.initFields(this, mapper);
		assert json != null; // required for FindBugs NP_UNWRITTEN_FIELD
	}
	
	@Test
	public void testValueToJson() throws Exception {
		// setup
		City tblCity = CityFixtures.createCity();
		SearchPrefectureCodeResponse sut = SearchPrefectureCodeResponseFixtures.createResponse(tblCity);
		// exercise
		JsonContent<SearchPrefectureCodeResponse> actual = json.write(sut);
		// verify
		assertThat(actual).extractingJsonPathStringValue("@.code").isEqualTo(sut.getCode());
		assertThat(actual).extractingJsonPathStringValue("@.city").isEqualTo(sut.getCity());
		assertThat(actual).extractingJsonPathStringValue("@.city_kana").isEqualTo(sut.getCityKana());
		assertThat(actual).extractingJsonPathStringValue("@.prefecture").isEqualTo(sut.getPrefecture());
		assertThat(actual).extractingJsonPathStringValue("@.prefecture_kana").isEqualTo(sut.getPrefectureKana());
		assertThat(actual).extractingJsonPathStringValue("@.prefecture_code").isEqualTo(sut.getPrefectureCode());
	}
	
	@Test
	public void testJsonToTreeToValue() throws Exception {
		// setup
		String areaJson = createAreaJson();
		
		// exercise
		JsonNode actualNode = mapper.readTree(areaJson);
		// verify
		assertThat(actualNode.path("code").isTextual()).isTrue();
		assertThat(actualNode.path("code").textValue()).isEqualTo("01102");
		assertThat(actualNode.path("city").isTextual()).isTrue();
		assertThat(actualNode.path("city").textValue()).isEqualTo("札幌市北区");
		assertThat(actualNode.path("city_kana").isTextual()).isTrue();
		assertThat(actualNode.path("city_kana").textValue()).isEqualTo("ｻｯﾎﾟﾛｼｷﾀｸ");
		assertThat(actualNode.path("prefecture").isTextual()).isTrue();
		assertThat(actualNode.path("prefecture").textValue()).isEqualTo("北海道");
		assertThat(actualNode.path("prefecture_kana").isTextual()).isTrue();
		assertThat(actualNode.path("prefecture_kana").textValue()).isEqualTo("ﾎｯｶｲﾄﾞｳ");
		assertThat(actualNode.path("prefecture_code").isTextual()).isTrue();
		assertThat(actualNode.path("prefecture_code").textValue()).isEqualTo("01");
	}
	
	private String createAreaJson() {
		return ""
				+ "{"
				+ "  'code':'01102',"
				+ "  'city':'札幌市北区',"
				+ "  'city_kana':'ｻｯﾎﾟﾛｼｷﾀｸ',"
				+ "  'prefecture':'北海道',"
				+ "  'prefecture_kana':'ﾎｯｶｲﾄﾞｳ',"
				+ "  'prefecture_code':'01'"
				+ "}";
	}
}
