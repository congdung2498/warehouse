package com.viettel.vtnet360.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import com.viettel.vtnet360.vt00.common.Constant;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "my_rest_api";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.anonymous().disable()
		.authorizeRequests()
		.antMatchers("/com/viettel/**").hasAnyAuthority(
				Constant.PMQT_ROLE_ADMIN,
				Constant.PMQT_ROLE_EMPLOYYEE,
				Constant.PMQT_ROLE_CHEF,
				Constant.PMQT_ROLE_EMPLOYEE_DOIXE,
				Constant.PMQT_ROLE_GUARD,
				Constant.PMQT_ROLE_MANAGER,
				Constant.PMQT_ROLE_MANAGER_CVP,
				Constant.PMQT_ROLE_MANAGER_DOIXE,
				Constant.PMQT_ROLE_STAFF,
				Constant.PMQT_ROLE_STAFF_HC_DV,
				Constant.PMQT_ROLE_STAFF_HCVP_VPP)
		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}