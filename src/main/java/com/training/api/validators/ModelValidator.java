package com.training.api.validators;

import com.training.api.utils.exceptions.InvalidModelException;

/**
 * Model Validator Interface
 *
 */
public interface ModelValidator {
    /**
     * Validate object.
     *
     * @param object validation target object
     * @param groups validate with validation groups.
     * @throws InvalidModelException if validation failed
     */
    void validate(Object object, Class<?>... groups);

}
