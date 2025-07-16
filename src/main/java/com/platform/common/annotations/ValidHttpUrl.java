package com.platform.common.annotations;

import com.platform.common.config.validator.RegexConstraintValidators;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {RegexConstraintValidators.HttpUrlValidator.class})
public @interface ValidHttpUrl {

    String message() default "Invalid URL format";

    /**
     * Regular expression pattern for validating HTTP URLs.
     * Default pattern matches HTTP and HTTPS URLs with optional port and path.
     */
    String pattern() default "^(https?:\\/\\/)?((([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,})|(\\d{1,3}(\\.\\d{1,3}){3}))(:\\d{2,5})?(\\/[^\\s]*)?$";

    Class<?>[] groups() default {};

    Class<? extends java.lang.annotation.Annotation>[] payload() default {};
}
