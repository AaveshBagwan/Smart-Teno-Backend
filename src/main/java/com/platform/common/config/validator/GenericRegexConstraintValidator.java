package com.platform.common.config.validator;

import com.platform.common.exception.InternalServerException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

/*
 * GenericRegexConstraintValidator is a generic validator that can be used with any annotation
 * that has a 'pattern' method returning a regex string.
 * It validates if the given string matches the regex pattern defined in the annotation.
 *
 * @param <A> The type of the annotation that this validator will handle.
 */
public class GenericRegexConstraintValidator<A extends Annotation> implements ConstraintValidator<A, String> {

    private Pattern regexPattern;

    @Override
    public void initialize(A constraintAnnotation) {
        try {
            String pattern = (String) constraintAnnotation.annotationType().getMethod("pattern").invoke(constraintAnnotation);
            this.regexPattern = Pattern.compile(pattern);
        } catch (NoSuchMethodException e) {
            throw new InternalServerException("Validator annotations must have a 'pattern' method with a default value", e);
        } catch (InvocationTargetException e) {
            throw new InternalServerException("Error while invoking pattern method for " + constraintAnnotation.getClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new InternalServerException("Unable to access the pattern method for " + constraintAnnotation.getClass().getName(), e);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return true; // Let other validations handle null or empty values
        }
        return regexPattern.matcher(value).matches();
    }


}
