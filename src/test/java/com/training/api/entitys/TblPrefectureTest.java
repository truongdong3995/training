package com.training.api.entitys;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.fixtures.TblPostFixtures;
import com.training.api.entitys.fixtures.TblPrefectureFixtures;
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
 * Test for {@link TblPrefecture}
 *
 */
public class TblPrefectureTest {
    JacksonTester<TblPrefecture> json;
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
        TblPrefecture sut = TblPrefectureFixtures.createPrefecture();
        // exercise
        Set<ConstraintViolation<TblPrefecture>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(0);
    }

    @Test
    public void testNotNullConstraints() {
        // setup
        TblPrefecture sut = new TblPrefecture();
        // exercise
        Set<ConstraintViolation<TblPrefecture>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(3);
    }

    @Test
    public void testValueToJson() throws Exception {
        // setup
        TblPrefecture tblPrefecture = TblPrefectureFixtures.createPrefecture();
        // exercise
        JsonContent<TblPrefecture> actual = json.write(tblPrefecture);
        // verify
        assertThat(actual).extractingJsonPathStringValue("@.prefecture_kana").isEqualTo("ﾎｯｶｲﾄﾞｳ");
        assertThat(actual).extractingJsonPathStringValue("@.prefecture").isEqualTo("北海道");
        assertThat(actual).extractingJsonPathStringValue("@.prefecture_code").isEqualTo("01");
    }

    @Test
    public void testValueToTree() throws Exception {
        // setup
        TblPrefecture tblPrefecture = TblPrefectureFixtures.createPrefecture();
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
