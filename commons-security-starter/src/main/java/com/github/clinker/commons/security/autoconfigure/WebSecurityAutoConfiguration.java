package com.github.clinker.commons.security.autoconfigure;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.security.auth.LoginFormAuthenticationFilter;
import com.github.clinker.commons.security.auth.RestAccessDeniedHandler;
import com.github.clinker.commons.security.auth.RestAuthenticationFailureHandler;
import com.github.clinker.commons.security.auth.RestAuthenticationSuccessHandler;
import com.github.clinker.commons.security.auth.RestLogoutHandler;
import com.github.clinker.commons.security.authz.UrlAccessDecisionVoter;
import com.github.clinker.commons.security.authz.UrlPermissionSecurityMetadataSource;
import com.github.clinker.commons.security.token.TokenAuthenticationFilter;

import lombok.AllArgsConstructor;

/**
 * Web安全配置。
 */
@Configuration
@AllArgsConstructor
public class WebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

	private final ObjectMapper objectMapper;

	private final RestAccessDeniedHandler restAccessDeniedHandler;

	private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;

	private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

	private final RestLogoutHandler restLogoutHandler;

	private final TokenAuthenticationFilter tokenAuthenticationFilter;

	private final UrlAccessDecisionVoter urlAccessDecisionVoter;

	private final UrlPermissionSecurityMetadataSource urlPermissionSecurityMetadataSource;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf()
				.disable()// 不需要csrf
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(final O fsi) {
						fsi.setSecurityMetadataSource(urlPermissionSecurityMetadataSource);
						fsi.setAccessDecisionManager(new AffirmativeBased(Arrays.asList(urlAccessDecisionVoter)));
						return fsi;
					}

				})
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // 未登录
				.accessDeniedHandler(restAccessDeniedHandler)// 已登录，无权限
				.and()
				.formLogin()
				.and()
				.logout()
				.addLogoutHandler(restLogoutHandler)
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

		// 用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
		final LoginFormAuthenticationFilter filter = new LoginFormAuthenticationFilter(objectMapper);
		filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
		filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
		filter.setPostOnly(true);
		filter.setAuthenticationManager(authenticationManagerBean());
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		// token过滤器
		http.addFilterBefore(tokenAuthenticationFilter, LoginFormAuthenticationFilter.class);
	}

}
