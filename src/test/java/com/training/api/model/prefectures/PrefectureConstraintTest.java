package com.training.api.model.prefectures;

import org.hibernate.internal.util.StringHelper;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link Prefecture}
 *
 */
public class PrefectureConstraintTest {
	
	final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	/**
	 * Test not null constraint.
	 */
	@Test
	public void testNotNullConstraints() {
		// setup
		Prefecture sut = new Prefecture();
		// exercise
		Set<ConstraintViolation<Prefecture>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"prefectureCode must not be null",
				"prefectureKana must not be null",
				"prefectureName must not be null");
		assertThat(actual).hasSize(3);
	}
	
	/**
	 * Test pattern constraint.
	 */
	@Test
	public void testPatternConstraints() {
		// setup
		Prefecture sut = new Prefecture();
		sut.setPrefectureCode(StringHelper.repeat("x", 8));
		sut.setPrefectureKana(StringHelper.repeat("x", 111));
		sut.setPrefectureName(StringHelper.repeat("x", 111));
		// exercise
		Set<ConstraintViolation<Prefecture>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"prefectureCode length must be between 0 and 2",
				"prefectureKana length must be between 0 and 100",
				"prefectureName length must be between 0 and 100");
		assertThat(actual).hasSize(3);
	}
}
