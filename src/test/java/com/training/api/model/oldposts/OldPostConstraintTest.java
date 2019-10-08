package com.training.api.model.oldposts;

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
 * Test for {@link OldPost}
 *
 */
@Slf4j
public class OldPostConstraintTest {
	
	final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	/**
	 * Test not null constraint.
	 */
	@Test
	public void testNotNullConstraints() {
		// setup
		OldPost sut = new OldPost();
		// exercise
		Set<ConstraintViolation<OldPost>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"oldPostCode must not be null");
		assertThat(actual).hasSize(1);
	}
	
	/**
	 * Test pattern constraint.
	 */
	@Test
	public void testPatternConstraints() {
		// setup
		OldPost sut = new OldPost();
		sut.setOldPostCode(StringHelper.repeat("x", 8));
		// exercise
		Set<ConstraintViolation<OldPost>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"oldPostCode length must be between 0 and 5");
		assertThat(actual).hasSize(1);
	}
}
