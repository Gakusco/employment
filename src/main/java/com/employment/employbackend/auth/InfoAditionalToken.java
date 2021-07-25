package com.employment.employbackend.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.employment.employbackend.model.Credential;
import com.employment.employbackend.model.Role;
import com.employment.employbackend.model.User;
import com.employment.employbackend.service.CredentialService;
import com.employment.employbackend.service.UserService;

@Component
public class InfoAditionalToken implements TokenEnhancer {

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private UserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Credential credential = credentialService.findByUsername(authentication.getName());
		List<User> userList = userService.findAll();
		Optional<User> user = userList.stream()
				.filter(u -> u.getCredential() != null && u.getCredential().getId() == credential.getId()).findFirst();
		Map<String, Object> info = new HashMap<>();
		info.put("role", credential.getRoles().stream().map(Role::getRole));
		info.put("credentialId", credential.getId());
		if (user.isPresent())
			info.put("userId", user.get().getId());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
