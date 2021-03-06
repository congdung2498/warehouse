package com.viettel.vtnet360.common.security;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
  
  @Override
  protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
    super.beforeSpringSecurityFilterChain(servletContext);

    // CSRF for multipart form data filter:
    FilterRegistration.Dynamic springMultipartFilter;
    springMultipartFilter = servletContext.addFilter("springMultipartFilter", new MultipartFilter());
    springMultipartFilter.addMappingForUrlPatterns(null, false, "/*");

  }
}
