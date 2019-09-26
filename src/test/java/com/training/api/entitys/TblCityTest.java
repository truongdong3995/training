package com.training.api.entitys;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.fixtures.TblAreaFixtures;
import com.training.api.entitys.fixtures.TblCityFixtures;
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
 * Test for {@link TblCity}
 *
 */
public class TblCityTest {
    JacksonTester<TblCity> json;
    private ObjectMapper mapper;
    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
        TblCity sut = TblCityFixtures.createCity();
        // exercise
        Set<ConstraintViolation<TblCity>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(0);
    }

    @Test
    public void testNotNullConstraints() {
        // setup
        TblCity sut = new TblCity();
        // exercise
        Set<ConstraintViolation<TblCity>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(3);
    }

    @Test
    public void testValueToJson() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        // exercise
        JsonContent<TblCity> actual = json.write(tblCity);
        // verify
        assertThat(actual).extractingJsonPathStringValue("@.code").isEqualTo("01102");
        assertThat(actual).extractingJsonPathStringValue("@.city_kana").isEqualTo("ｻｯﾎﾟﾛｼｷﾀ");
        assertThat(actual).extractingJsonPathStringValue("@.city").isEqualTo("札幌市北区");
        assertThat(actual).extractingJsonPathStringValue("@.tblPrefecture.prefecture_kana").isEqualTo("ﾎｯｶｲﾄﾞｳ");
        assertThat(actual).extractingJsonPathStringValue("@.tblPrefecture.prefecture").isEqualTo("北海道");
        assertThat(actual).extractingJsonPathStringValue("@.tblPrefecture.prefecture_code").isEqualTo("01");
    }

    @Test
    public void testValueToTree() throws Exception {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        // exercise: Value to Tree
        JsonNode actualNode = mapper.valueToTree(tblCity);
        assertThat(actualNode.path("code").isTextual()).isTrue();
        assertThat(actualNode.path("code").textValue()).isEqualTo("01102");
        assertThat(actualNode.path("city_kana").isTextual()).isTrue();
        assertThat(actualNode.path("city_kana").textValue()).isEqualTo("ｻｯﾎﾟﾛｼｷﾀ");
        assertThat(actualNode.path("city").isTextual()).isTrue();
        assertThat(actualNode.path("city").textValue()).isEqualTo("札幌市北区");
        assertThat(actualNode.path("tblPrefecture").path("prefecture_kana").isTextual()).isTrue();
        assertThat(actualNode.path("tblPrefecture").path("prefecture_kana").textValue()).isEqualTo("ﾎｯｶｲﾄﾞｳ");
        assertThat(actualNode.path("tblPrefecture").path("prefecture").isTextual()).isTrue();
        assertThat(actualNode.path("tblPrefecture").path("prefecture").textValue()).isEqualTo("北海道");
        assertThat(actualNode.path("tblPrefecture").path("prefecture_code").isTextual()).isTrue();
        assertThat(actualNode.path("tblPrefecture").path("prefecture_code").textValue()).isEqualTo("01");
    }
}
