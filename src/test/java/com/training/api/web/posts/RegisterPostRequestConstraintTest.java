package com.training.api.web.posts;

import org.hibernate.internal.util.StringHelper;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test constrain {@link RegisterPostRequest}.
 */
public class RegisterPostRequestConstraintTest {
	
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	@Test
	public void testPatternConstraints() {
		// setup
		RegisterPostRequest sut = new RegisterPostRequest();
		sut.setPostCode(StringHelper.repeat("x", 8));
		// exercise
		Set<ConstraintViolation<RegisterPostRequest>> actual = validator.validate(sut);
		// verify
		Set<String> errorExpression = actual.stream()
			.map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
			.collect(Collectors.toSet());
		assertThat(errorExpression).containsExactlyInAnyOrder(
				"postCode length must be between 0 and 7");
		assertThat(actual).hasSize(1);
	}
}
