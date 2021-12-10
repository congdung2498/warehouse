package com.viettel.vtnet360.common.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@PropertySource(value = { "classpath:environment.properties" })
public class ApplicationConfig {

	@Autowired
	private Environment env;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//dataSource.setDriverClassName(EnDecryptUtils.decrypt(env.getRequiredProperty("jdbc.driverClassName")));
		//dataSource.setUrl(EnDecryptUtils.decrypt(env.getRequiredProperty("jdbc.url")));
		//dataSource.setUsername(EnDecryptUtils.decrypt(env.getRequiredProperty("jdbc.username")));
		//dataSource.setPassword(EnDecryptUtils.decrypt(env.getRequiredProperty("jdbc.password")));
		
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));	
		
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}

	@Bean(name = "filterMultipartResolver")
	public CommonsMultipartResolver multiPartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setMaxUploadSize(Long.MAX_VALUE);
		return resolver;
	}
}