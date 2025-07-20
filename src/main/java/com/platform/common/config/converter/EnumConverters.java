package com.platform.common.config.converter;

import com.platform.common.model.jwt.JwtSubKeys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class EnumConverters {

    @Bean
    public Converter<String, JwtSubKeys> getJwtSubkeysConverter() {
        return new GenericPropsToEnumConverter<>(JwtSubKeys.class);
    }

}
