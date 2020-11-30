package com.github.clinker.commons.security.token;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.clinker.commons.security.AuthAccountUserDetails;

/**
 * 认证token过滤器。
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final GrantedAuthorityConverter grantedAuthorityConverter;

	private final TokenProperties tokenProperties;

	private final TokenService tokenService;

	public TokenAuthenticationFilter(final GrantedAuthorityConverter grantedAuthorityConverter,
			final TokenProperties tokenProperties, final TokenService tokenService) {
		this.grantedAuthorityConverter = grantedAuthorityConverter;
		this.tokenProperties = tokenProperties;
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		final String tokenHeaderValue = request.getHeader(tokenProperties.getHeader());

		if (StringUtils.isNotBlank(tokenHeaderValue)) {
			final String token = StringUtils
					.removeStartIgnoreCase(tokenHeaderValue, tokenProperties.getHeaderValuePrefix())
					.trim();
			final TokenValue tokenValue = tokenService.findByToken(token);
			if (tokenValue != null && SecurityContextHolder.getContext()
					.getAuthentication() == null) {

				// 延期
				tokenService.extend(token);

				final Collection<? extends GrantedAuthority> authorities = grantedAuthorityConverter
						.decode(tokenValue.getAuthorities());
				// 查询用户
				final AuthAccountUserDetails userDetails = new AuthAccountUserDetails(tokenValue.getAccountId(),
						tokenValue.getUsername(), ""/* password */, authorities);

				final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, userDetails.getPassword(), authorities);

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// 认证成功
				SecurityContextHolder.getContext()
						.setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}

}
