package com.training.api.model.areas;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link Area}
 *
 */
@Slf4j
public class AreaConstraintTest {
	
	final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	/**
	 * Test not null constraint.
	 */
	@Test
	public void testNotNullConstraints() {
		// setup
		Area sut = new Area();
		// exercise
		Set<ConstraintViolation<Area>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"areaName must not be null",
				"areaKana must not be null");
		assertThat(actual).hasSize(2);
	}
}
