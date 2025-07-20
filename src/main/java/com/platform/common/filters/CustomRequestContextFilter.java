package com.platform.common.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.RequestContextFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomRequestContextFilter extends RequestContextFilter {
}
