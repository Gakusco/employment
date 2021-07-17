package com.employment.employbackend.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.employment.employbackend.model.Credential;
import com.employment.employbackend.model.Role;
import com.employment.employbackend.service.CredentialService;

@Component
public class InfoAditionalToken implements TokenEnhancer {

	@Autowired
	private CredentialService credentialService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Credential credential = credentialService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("role", credential.getRoles().stream().map(Role::getRole));
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
