package com.platform.common.config.validator;

import com.platform.common.annotations.ValidHttpUrl;
import com.platform.common.annotations.ValidMobileNumber;
import com.platform.common.annotations.ValidPassword;
import com.platform.common.annotations.ValidUrl;

public class RegexConstraintValidators {

    public static class HttpUrlValidator extends GenericRegexConstraintValidator<ValidHttpUrl> {}

    public static class UrlValidator extends GenericRegexConstraintValidator<ValidUrl> {}

    public static class PasswordValidator extends GenericRegexConstraintValidator<ValidPassword> {}

    public static class MobileNumberValidator extends GenericRegexConstraintValidator<ValidMobileNumber> {}

}
