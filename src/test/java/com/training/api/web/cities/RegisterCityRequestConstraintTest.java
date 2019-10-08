package com.training.api.web.cities;

import org.hibernate.internal.util.StringHelper;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test constrain {@link RegisterCityRequest}.
 */
public class RegisterCityRequestConstraintTest {
	
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	@Test
	public void testPatternConstraints() {
		// setup
		RegisterCityRequest sut = new RegisterCityRequest();
		sut.setCityCode(StringHelper.repeat("x", 8));
		sut.setCityKana(null);
		// exercise
		Set<ConstraintViolation<RegisterCityRequest>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"cityCode length must be between 0 and 5",
				"cityKana must not be null",
				"prefecture must not be null",
				"cityName must not be null");
		assertThat(actual).hasSize(4);
	}
}
