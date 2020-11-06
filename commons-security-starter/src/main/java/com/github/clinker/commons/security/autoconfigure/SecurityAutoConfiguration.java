package com.github.clinker.commons.security.autoconfigure;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.security.AuthAccountUserDetailsServiceImpl;
import com.github.clinker.commons.security.TenantProperties;
import com.github.clinker.commons.security.auth.RestAccessDeniedHandler;
import com.github.clinker.commons.security.auth.RestAuthenticationFailureHandler;
import com.github.clinker.commons.security.auth.RestAuthenticationSuccessHandler;
import com.github.clinker.commons.security.auth.RestLogoutHandler;
import com.github.clinker.commons.security.authz.AuthzCacheService;
import com.github.clinker.commons.security.authz.AuthzCacheServiceImpl;
import com.github.clinker.commons.security.authz.AuthzProperties;
import com.github.clinker.commons.security.authz.ConfigAttributeService;
import com.github.clinker.commons.security.authz.ConfigAttributeServiceImpl;
import com.github.clinker.commons.security.authz.UrlAccessDecisionVoter;
import com.github.clinker.commons.security.authz.UrlPermissionSecurityMetadataSource;
import com.github.clinker.commons.security.authz.event.AuthzRefreshEventReceiver;
import com.github.clinker.commons.security.repository.AuthAccountRepository;
import com.github.clinker.commons.security.repository.AuthPermissionRepository;
import com.github.clinker.commons.security.repository.AuthRoleRepository;
import com.github.clinker.commons.security.repository.impl.AuthAccountRepositoryImpl;
import com.github.clinker.commons.security.repository.impl.AuthPermissionRepositoryImpl;
import com.github.clinker.commons.security.repository.impl.AuthRoleRepositoryImpl;
import com.github.clinker.commons.security.token.GrantedAuthorityConverter;
import com.github.clinker.commons.security.token.TokenAuthenticationFilter;
import com.github.clinker.commons.security.token.TokenGenerator;
import com.github.clinker.commons.security.token.TokenGeneratorUuid;
import com.github.clinker.commons.security.token.TokenProperties;
import com.github.clinker.commons.security.token.TokenService;
import com.github.clinker.commons.security.token.TokenServiceRedis;

import lombok.AllArgsConstructor;

/**
 * Security自动配置。
 */
@Configuration
@AutoConfigureAfter({ JacksonAutoConfiguration.class, DataSourceAutoConfiguration.class, RedisAutoConfiguration.class })
@EnableConfigurationProperties({ TokenProperties.class, TenantProperties.class, AuthzProperties.class })
@AllArgsConstructor
class SecurityAutoConfiguration {

	@Configuration
	static class AuthzConfiguration implements InitializingBean {

		@Autowired
		private AuthPermissionRepository authPermissionRepository;

		@Autowired
		private AuthRoleRepository authRoleRepository;

		@Autowired
		private AuthzProperties authzProperties;

		@Autowired
		private ApplicationEventPublisher publisher;

		@Autowired
		private TenantProperties tenantProperties;

		@Override
		public void afterPropertiesSet() throws Exception {
			// 必须设置serviceId
			if (StringUtils.isBlank(tenantProperties.getServiceId())) {
				throw new Exception("必须设置属性auth.service-id");
			}
		}

		@Bean
		@ConditionalOnMissingBean
		public AuthzCacheService authzCacheService() {
			return new AuthzCacheServiceImpl(authRoleRepository, configAttributeService(), tenantProperties);
		}

		@Bean
		@ConditionalOnMissingBean
		public ConfigAttributeService configAttributeService() {
			return new ConfigAttributeServiceImpl(authPermissionRepository, authRoleRepository, tenantProperties);
		}

		@Bean
		public RedisMessageListenerContainer redisContainer(final RedisConnectionFactory connectionFactory) {
			final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
			container.setConnectionFactory(connectionFactory);

			final MessageListenerAdapter adapter = new MessageListenerAdapter(new AuthzRefreshEventReceiver(publisher),
					"receive");
			adapter.afterPropertiesSet();

			container.addMessageListener(adapter, new ChannelTopic(authzProperties.getCacheTopic()));

			return container;
		}

		@Bean
		@ConditionalOnMissingBean
		public UrlAccessDecisionVoter urlAccessDecisionVoter() {
			return new UrlAccessDecisionVoter(authzCacheService());
		}

		@Bean
		@ConditionalOnMissingBean
		public UrlPermissionSecurityMetadataSource urlPermissionSecurityMetadataSource() {
			return new UrlPermissionSecurityMetadataSource(authzCacheService());
		}

	}

	private final DataSource dataSource;

	private final ObjectMapper objectMapper;

	private final StringRedisTemplate stringRedisTemplate;

	@Autowired
	private final TenantProperties tenantProperties;

	private final TokenProperties tokenProperties;

	@Bean
	@ConditionalOnMissingBean
	public AuthAccountRepository authAccountRepository() {
		return new AuthAccountRepositoryImpl(dataSource);
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthPermissionRepository authPermissionRepository() {
		return new AuthPermissionRepositoryImpl(dataSource);
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthRoleRepository authRoleRepository() {
		return new AuthRoleRepositoryImpl(dataSource);
	}

	@Bean
	@ConditionalOnMissingBean
	public GrantedAuthorityConverter grantedAuthorityConverter() {
		return new GrantedAuthorityConverter();
	}

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@ConditionalOnMissingBean
	public RestAccessDeniedHandler restAccessDeniedHandler() {
		return new RestAccessDeniedHandler(objectMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public RestAuthenticationFailureHandler restAuthenticationFailureHandler() {
		return new RestAuthenticationFailureHandler(objectMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public RestAuthenticationSuccessHandler restAuthenticationSuccessHandler() {
		return new RestAuthenticationSuccessHandler(grantedAuthorityConverter(), objectMapper, tokenProperties,
				tokenService());
	}

	@Bean
	@ConditionalOnMissingBean
	public RestLogoutHandler restLogoutHandler() {
		return new RestLogoutHandler(tokenProperties, tokenService());
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter(grantedAuthorityConverter(), tokenProperties, tokenService());
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenGenerator tokenGenerator() {
		return new TokenGeneratorUuid();
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenService tokenService() {
		return new TokenServiceRedis(objectMapper, stringRedisTemplate, tokenGenerator(), tokenProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public UserDetailsService userDetailsService() {
		return new AuthAccountUserDetailsServiceImpl(authAccountRepository(), authRoleRepository(), tenantProperties);
	}

}
