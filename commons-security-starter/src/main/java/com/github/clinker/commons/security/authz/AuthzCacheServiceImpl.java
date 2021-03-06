package com.github.clinker.commons.security.authz;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

import com.github.clinker.commons.security.TenantProperties;
import com.github.clinker.commons.security.authz.event.AuthzRefreshEvent;
import com.github.clinker.commons.security.entity.AuthRole;
import com.github.clinker.commons.security.repository.AuthRoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 授权缓存服务实现。
 */
@Slf4j
public class AuthzCacheServiceImpl implements AuthzCacheService {

	private final AuthRoleRepository authRoleRepository;

	/**
	 * 超级角色缓存key
	 */
	private final String AUTHZ_SUPER_ROLE_IDENTIFIER = "AUTHZ_SUPER_ROLE_IDENTIFIER";

	private final ConfigAttributeService configAttributeService;

	/**
	 * FilterInvocation关联的ConfigAttribute缓存
	 */
	private final Map<FilterInvocationCacheKey, Collection<ConfigAttribute>> filterInvocationCache = new ConcurrentHashMap<>();

	/**
	 * 超级角色缓存
	 */
	private final Map<String, Set<String>> superRoleIdentifierCache = new ConcurrentHashMap<>();

	private final TenantProperties tenantProperties;

	public AuthzCacheServiceImpl(final AuthRoleRepository authRoleRepository,
			final ConfigAttributeService configAttributeService, final TenantProperties tenantProperties) {
		this.authRoleRepository = authRoleRepository;
		this.configAttributeService = configAttributeService;
		this.tenantProperties = tenantProperties;
	}

	@Override
	public void clear() {
		log.info("Clear authz cache");

		filterInvocationCache.clear();

		superRoleIdentifierCache.clear();
	}

	@Override
	public Collection<ConfigAttribute> findByFilterInvocation(final FilterInvocation filterInvocation) {
		return filterInvocationCache.computeIfAbsent(
				new FilterInvocationCacheKey(filterInvocation.getRequestUrl(), filterInvocation.getHttpRequest()
						.getMethod()),
				filterInvocationCacheKey -> configAttributeService.findByFilterInvocation(filterInvocation));
	}

	@Override
	public Set<String> findSuperRoleIdentifiers() {
		return superRoleIdentifierCache.computeIfAbsent(AUTHZ_SUPER_ROLE_IDENTIFIER, key -> {
			final List<AuthRole> superRoles = authRoleRepository.findSuper(tenantProperties.getServiceId());
			if (superRoles != null) {
				return superRoles.parallelStream()
						.map(AuthRole::getIdentifier)
						.collect(Collectors.toSet());
			} else {
				return Collections.emptySet();
			}
		});
	}

	/**
	 * 处理授权缓存刷新。
	 *
	 * @param event 授权缓存刷新事件
	 */
	@EventListener
	public void refresh(final AuthzRefreshEvent event) {
		clear();
	}

}
