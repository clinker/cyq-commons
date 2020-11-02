package com.github.clinker.commons.security.authz;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * URL permission实现。
 */
public class UrlPermissionSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final AuthzCacheService authzCacheService;

	public UrlPermissionSecurityMetadataSource(final AuthzCacheService authzCacheService) {
		this.authzCacheService = authzCacheService;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * 查询URL关联了哪些角色。返回null或空集合表示该URL不需要授权。
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(final Object object) throws IllegalArgumentException {
		return authzCacheService.findByFilterInvocation((FilterInvocation) object);
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
