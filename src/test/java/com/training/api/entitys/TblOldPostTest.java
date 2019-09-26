package com.training.api.entitys;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entitys.fixtures.TblCityFixtures;
import com.training.api.entitys.fixtures.TblOldPostFixtures;
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
 * Test for {@link TblOldPost}
 *
 */
public class TblOldPostTest {
    JacksonTester<TblOldPost> json;
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
        TblOldPost sut = TblOldPostFixtures.createOldPost();
        // exercise
        Set<ConstraintViolation<TblOldPost>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(0);
    }

    @Test
    public void testNotNullConstraints() {
        // setup
        TblOldPost sut = new TblOldPost();
        // exercise
        Set<ConstraintViolation<TblOldPost>> actual = validator.validate(sut);
        // verify
        assertThat(actual).hasSize(1);
    }

    @Test
    public void testValueToJson() throws Exception {
        // setup
        TblOldPost tblOldPost = TblOldPostFixtures.createOldPost();
        // exercise
        JsonContent<TblOldPost> actual = json.write(tblOldPost);
        // verify
        assertThat(actual).extractingJsonPathStringValue("@.old_post_code").isEqualTo("001");
    }

    @Test
    public void testValueToTree() throws Exception {
        // setup
        TblOldPost tblOldPost = TblOldPostFixtures.createOldPost();
        // exercise: Value to Tree
        JsonNode actualNode = mapper.valueToTree(tblOldPost);
        assertThat(actualNode.path("old_post_code").isTextual()).isTrue();
        assertThat(actualNode.path("old_post_code").textValue()).isEqualTo("001");
    }
}
