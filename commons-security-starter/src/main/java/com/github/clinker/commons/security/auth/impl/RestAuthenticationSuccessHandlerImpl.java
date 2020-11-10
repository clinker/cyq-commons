package com.github.clinker.commons.security.auth.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.security.auth.RestAuthenticationSuccessHandler;
import com.github.clinker.commons.security.token.GrantedAuthorityConverter;
import com.github.clinker.commons.security.token.TokenProperties;
import com.github.clinker.commons.security.token.TokenService;

/**
 * 默认返回LoginResult。
 */
public class RestAuthenticationSuccessHandlerImpl extends RestAuthenticationSuccessHandler {

	public RestAuthenticationSuccessHandlerImpl(final GrantedAuthorityConverter grantedAuthorityConverter,
			final ObjectMapper objectMapper, final TokenProperties tokenProperties, final TokenService tokenService) {
		super(grantedAuthorityConverter, objectMapper, tokenProperties, tokenService);
	}

}
