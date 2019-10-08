package com.training.api.model.posts;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.StringHelper;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link Post}
 *
 */
@Slf4j
public class PostConstraintTest {
	
	final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	/**
	 * Test not null constraint.
	 */
	@Test
	public void testNotNullConstraints() {
		// setup
		Post sut = new Post();
		// exercise
		Set<ConstraintViolation<Post>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"postCode must not be null");
		assertThat(actual).hasSize(1);
	}
	
	/**
	 * Test pattern constraint.
	 */
	@Test
	public void testPatternConstraints() {
		// setup
		Post sut = new Post();
		sut.setPostCode(StringHelper.repeat("x", 8));
		// exercise
		Set<ConstraintViolation<Post>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"postCode length must be between 0 and 7");
		assertThat(actual).hasSize(1);
	}
}
