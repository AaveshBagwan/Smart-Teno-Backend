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
@Constraint(validatedBy = {RegexConstraintValidators.MobileNumberValidator.class})
public @interface ValidMobileNumber {

    String message() default "Invalid mobile number format";

    /**
     * Regular expression pattern for validating mobile numbers.
     * Default pattern matches Indian mobile numbers starting with 6, 7, 8, or 9 and followed by 9 digits.
     */
    String pattern() default "^[6789]\\d{9}$";

    Class<?>[] groups() default {};

    Class<? extends java.lang.annotation.Annotation>[] payload() default {};
}
