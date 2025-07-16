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
@Constraint(validatedBy = {RegexConstraintValidators.UrlValidator.class})
public @interface ValidUrl {

    String message() default "Invalid URL format";

    /**
     * Regular expression pattern for validating URLs.
     * Default pattern matches HTTP, HTTPS, FTP, mailto, and file URLs.
     */
    String pattern() default "^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$|^mailto:[^\\s]+@[^\\s]+\\.[^\\s]+$|^file:\\/\\/\\/[^\\s]+$";

    Class<?>[] groups() default {};

    Class<? extends java.lang.annotation.Annotation>[] payload() default {};
}
