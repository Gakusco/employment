package com.employment.employbackend.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String ROLE_SUPERVISOR = "SUPERVISOR";
	private static final String ROLE_POSTULANT = "POSTULANT";

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/auth/register", "/").permitAll().antMatchers("/statistics/**")
				.hasRole(ROLE_SUPERVISOR).antMatchers("/business/**").hasRole(ROLE_SUPERVISOR)
				.antMatchers("/job-offer/postulate/{postulantId}/{jobOfferId}")
				.hasAnyRole(ROLE_SUPERVISOR, ROLE_POSTULANT).antMatchers("/job-offer/list")
				.hasAnyRole(ROLE_SUPERVISOR, ROLE_POSTULANT).antMatchers("/job-offer/**").hasRole(ROLE_SUPERVISOR)
				.antMatchers("/postulant/applications/{postulantId}").hasAnyRole(ROLE_SUPERVISOR, ROLE_POSTULANT)
				.antMatchers("/postulant/**").hasRole(ROLE_SUPERVISOR).anyRequest().authenticated().and().cors()
				.configurationSource(corsConfigurationSource());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
