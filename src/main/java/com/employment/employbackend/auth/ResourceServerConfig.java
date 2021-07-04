package com.employment.employbackend.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String ROLE_SUPERVISOR = "SUPERVISOR";
	private static final String ROLE_POSTULANT = "POSTULANT";

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/auth/register", "/").permitAll().antMatchers("/statistics/**")
				.hasRole(ROLE_SUPERVISOR).antMatchers("/business/**").hasRole(ROLE_SUPERVISOR)
				.antMatchers("/job-offer/postulate/{postulantId}/{jobOfferId}").hasAnyRole(ROLE_SUPERVISOR, ROLE_POSTULANT)
				.antMatchers("/job-offer/list").hasAnyRole(ROLE_SUPERVISOR, ROLE_POSTULANT).antMatchers("/job-offer/**")
				.hasRole(ROLE_SUPERVISOR).antMatchers("/postulant/applications/{postulantId}")
				.hasAnyRole(ROLE_SUPERVISOR, ROLE_POSTULANT).antMatchers("/postulant/**").hasRole(ROLE_SUPERVISOR).anyRequest()
				.authenticated();
	}

}
