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
@Constraint(validatedBy = {RegexConstraintValidators.PasswordValidator.class})
public @interface ValidPassword {

    String message() default "Invalid password, at least 8 characters long, containing at least one uppercase letter, one lowercase letter, one digit, and one special character.";

    /**
     * Regular expression pattern for validating passwords.
     * Default pattern requires at least 8 characters, one uppercase letter, one lowercase letter,
     * one digit, and one special character.
     */
    String pattern() default "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    Class<?>[] groups() default {};

    Class<? extends java.lang.annotation.Annotation>[] payload() default {};

}
