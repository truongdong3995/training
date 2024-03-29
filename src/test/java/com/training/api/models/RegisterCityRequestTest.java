package com.training.api.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.models.fixtures.RegisterCityRequestFixtures;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link RegisterCityRequest}
 *
 */
public class RegisterCityRequestTest {
    JacksonTester<RegisterCityRequest> json;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        JacksonTester.initFields(this, mapper);
        assert json != null;
    }

    @Test
    public void testValueToJson() throws Exception {
        // setup
        RegisterCityRequest registerCityRequest = RegisterCityRequestFixtures.creatRequest();
        // exercise
        JsonContent<RegisterCityRequest> actual = json.write(registerCityRequest);
        // verify
        assertThat(actual).extractingJsonPathStringValue("@.code").isEqualTo(registerCityRequest.getCode());
        assertThat(actual).extractingJsonPathStringValue("@.city_kana").isEqualTo(registerCityRequest.getCityKana());
        assertThat(actual).extractingJsonPathStringValue("@.city").isEqualTo(registerCityRequest.getCity());
        assertThat(actual).extractingJsonPathNumberValue("@.tblPrefecture.prefecture_id").isEqualTo(258);
        assertThat(actual).extractingJsonPathStringValue("@.tblPrefecture.prefecture_kana").isEqualTo("ｵｷﾅﾜｹﾝ");
        assertThat(actual).extractingJsonPathStringValue("@.tblPrefecture.prefecture").isEqualTo("沖縄県");
        assertThat(actual).extractingJsonPathStringValue("@.tblPrefecture.prefecture_code").isEqualTo("47");
    }

    @Test
    public void testJsonToTreeToValue() throws Exception {
        // setup
        String areaJson = createAreaJson();

        // exercise
        JsonNode actualNode = mapper.readTree(areaJson);
        // verify
        assertThat(actualNode.path("tblPrefecture").path("prefecture_id").isNumber()).isTrue();
        assertThat(actualNode.path("tblPrefecture").path("prefecture_id").numberValue()).isEqualTo(192);
        assertThat(actualNode.path("tblPrefecture").path("prefecture_kana").isTextual()).isTrue();
        assertThat(actualNode.path("tblPrefecture").path("prefecture_kana").textValue()).isEqualTo("ﾎｯｶｲﾄﾞｳ");
        assertThat(actualNode.path("tblPrefecture").path("prefecture").isTextual()).isTrue();
        assertThat(actualNode.path("tblPrefecture").path("prefecture").textValue()).isEqualTo("北海道");
        assertThat(actualNode.path("tblPrefecture").path("prefecture_code").isTextual()).isTrue();
        assertThat(actualNode.path("tblPrefecture").path("prefecture_code").textValue()).isEqualTo("01");
        assertThat(actualNode.path("code").isTextual()).isTrue();
        assertThat(actualNode.path("code").textValue()).isEqualTo("01102");
        assertThat(actualNode.path("city_kana").isTextual()).isTrue();
        assertThat(actualNode.path("city_kana").textValue()).isEqualTo("ｻｯﾎﾟﾛｼｷﾀｸ");
        assertThat(actualNode.path("city").isTextual()).isTrue();
        assertThat(actualNode.path("city").textValue()).isEqualTo("札幌市北区");
    }

    private String createAreaJson() {
        return ""
                + "{"
                + "  'tblPrefecture':{"
                + "     'prefecture_id':192,"
                + "     'prefecture_kana':'ﾎｯｶｲﾄﾞｳ',"
                + "     'prefecture':'北海道',"
                + "     'prefecture_code':'01'"
                + "     },"
                + "  'code':'01102',"
                + "  'city_kana':'ｻｯﾎﾟﾛｼｷﾀｸ',"
                + "  'city':'札幌市北区'"
                + "}";
    }
}
