package com.training.api.web.cities;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test constrain {@link UpdateCityRequest}.
 *
 */
public class UpdateCityRequestConstraintTest {
	
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	@Test
	public void testPatternConstraints() {
		// setup
		UpdateCityRequest sut = new UpdateCityRequest();
		// exercise
		Set<ConstraintViolation<UpdateCityRequest>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"cityKana must not be null",
				"prefecture must not be null",
				"cityName must not be null");
		assertThat(actual).hasSize(3);
	}
}
