package com.training.api.entitys;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.fixtures.TblCityFixtures;
import com.training.api.entitys.fixtures.TblOldPostFixtures;
import com.training.api.entitys.fixtures.TblPostFixtures;
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
 * Test for {@link TblPost}
 *
 */
public class TblPostTest {
    JacksonTester<TblPost> json;
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
        TblPost sut = TblPostFixtures.createPost();
        // exercise
        Set<ConstraintViolation<TblPost>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(0);
    }

    @Test
    public void testNotNullConstraints() {
        // setup
        TblPost sut = new TblPost();
        // exercise
        Set<ConstraintViolation<TblPost>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(1);
    }

    @Test
    public void testValueToJson() throws Exception {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        // exercise
        JsonContent<TblPost> actual = json.write(tblPost);
        // verify
        assertThat(actual).extractingJsonPathStringValue("@.post_code").isEqualTo("0010000");
        assertThat(actual).extractingJsonPathNumberValue("@.update_show").isEqualTo(0);
        assertThat(actual).extractingJsonPathNumberValue("@.change_reason").isEqualTo(0);
        assertThat(actual).extractingJsonPathNumberValue("@.multi_area").isEqualTo(0);
    }

    @Test
    public void testValueToTree() throws Exception {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        // exercise: Value to Tree
        JsonNode actualNode = mapper.valueToTree(tblPost);
        assertThat(actualNode.path("post_code").isTextual()).isTrue();
        assertThat(actualNode.path("post_code").textValue()).isEqualTo("0010000");
        assertThat(actualNode.path("update_show").isNumber()).isTrue();
        assertThat(actualNode.path("update_show").numberValue()).isEqualTo(0);
        assertThat(actualNode.path("change_reason").isNumber()).isTrue();
        assertThat(actualNode.path("change_reason").numberValue()).isEqualTo(0);
        assertThat(actualNode.path("multi_area").isNumber()).isTrue();
        assertThat(actualNode.path("multi_area").numberValue()).isEqualTo(0);
    }
}
