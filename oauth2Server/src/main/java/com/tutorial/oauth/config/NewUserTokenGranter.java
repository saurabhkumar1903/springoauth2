package com.tutorial.oauth.config;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;

import com.tutorial.oauth.model.CustomUser;
import com.tutorial.oauth.model.UserEntity;
import com.tutorial.oauth.service.CustomUserDetailsService;

public class NewUserTokenGranter extends AbstractTokenGranter {
	static final String GRANT_TYPE = "signup";
	private CustomUserDetailsService customUserDetailsService;

	NewUserTokenGranter(CustomUserDetailsService customUserDetailsService,
			AuthorizationServerTokenServices tokenServices, OAuth2RequestFactory requestFactory,
			ClientDetailsService clientDetailsService) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = tokenRequest.getRequestParameters();
		try {
			if (customUserDetailsService.exist(parameters.get("username"))) {
				throw new Exception(
						"User '" + parameters.get("email") + "' already exists. Please choose a different username");
			}
			if (!StringUtils.hasText(parameters.get("password"))) {
				throw new Exception("Invalid password: null");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		UserEntity user = new UserEntity(parameters.get("name"), parameters.get("username"), parameters.get("password"));

		customUserDetailsService.create(user);
		CustomUser customUserDetails = new CustomUser(user);
		Authentication userAuth = new UsernamePasswordAuthenticationToken(customUserDetails, null,
				customUserDetails.getAuthorities());
		return new OAuth2Authentication(tokenRequest.createOAuth2Request(client), userAuth);
	}
}