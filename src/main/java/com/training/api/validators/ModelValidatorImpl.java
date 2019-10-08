package com.training.api.validators;

import com.training.api.model.InvalidModelException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Model Validator Implementation.
 *
 */
@RequiredArgsConstructor
@Component
public class ModelValidatorImpl implements ModelValidator {

    private final Validator validator;

    @Override
    public void validate(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object, groups);
        if (violations.isEmpty() == false) {
            String message = violations.stream()
                    .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                    .map(v -> v.getPropertyPath() + ":" + v.getMessage())
                    .collect(Collectors.joining(","));
            throw new InvalidModelException(message);
        }
    }
}
