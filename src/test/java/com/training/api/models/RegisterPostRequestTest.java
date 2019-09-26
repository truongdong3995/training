package com.training.api.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.models.fixtures.RegisterPostRequestFixtures;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link RegisterPostRequest}
 *
 */
public class RegisterPostRequestTest {
    JacksonTester<RegisterPostRequest> json;

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
        RegisterPostRequest registerPostRequest = RegisterPostRequestFixtures.createRequest();
        // exercise
        JsonContent<RegisterPostRequest> actual = json.write(registerPostRequest);
        // verify
        assertThat(actual).extractingJsonPathStringValue("@.post_code")
                .isEqualTo(registerPostRequest.getPostCode());
        assertThat(actual).extractingJsonPathNumberValue("@.update_show")
                .isEqualTo(registerPostRequest.getUpdateShow());
        assertThat(actual).extractingJsonPathNumberValue("@.change_reason")
                .isEqualTo(registerPostRequest.getChangeReason());
        assertThat(actual).extractingJsonPathNumberValue("@.multi_area")
                .isEqualTo(registerPostRequest.getMultiArea());
    }

    @Test
    public void testJsonToTreeToValue() throws Exception {
        // setup
        String areaJson = createAreaJson();

        // exercise
        JsonNode actualNode = mapper.readTree(areaJson);
        // verify
        assertThat(actualNode.path("post_code").isTextual()).isTrue();
        assertThat(actualNode.path("post_code").textValue()).isEqualTo("0010000");
        assertThat(actualNode.path("update_show").isNumber()).isTrue();
        assertThat(actualNode.path("update_show").numberValue()).isEqualTo(0);
        assertThat(actualNode.path("change_reason").isNumber()).isTrue();
        assertThat(actualNode.path("change_reason").numberValue()).isEqualTo(0);
        assertThat(actualNode.path("multi_area").isNumber()).isTrue();
        assertThat(actualNode.path("multi_area").numberValue()).isEqualTo(0);
    }

    private String createAreaJson() {
        return ""
                + "{"
                + "  'post_code':'0010000',"
                + "  'update_show':0,"
                + "  'change_reason':0,"
                + "  'multi_area':0"
                + "}";
    }
}
