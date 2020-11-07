package com.github.clinker.commons.security.authz;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.github.clinker.commons.security.TenantProperties;
import com.github.clinker.commons.security.entity.AuthPermission;
import com.github.clinker.commons.security.entity.AuthRole;
import com.github.clinker.commons.security.repository.AuthPermissionRepository;
import com.github.clinker.commons.security.repository.AuthRoleRepository;

/**
 * ConfigAttribute与FilterInvocation关联服务实现。
 * <p/>
 * 规则：
 * <ul>
 * <li>{@link AuthPermission}未定义的URL，拒绝访问；</li>
 * <li>超级角色可以访问所有URL。</li>
 * </ul>
 */
public class ConfigAttributeServiceImpl implements ConfigAttributeService {

	private static final String ANY_METHOD = "*";

	private final AuthPermissionRepository authPermissionRepository;

	private final AuthRoleRepository authRoleRepository;

	private final TenantProperties tenantProperties;

	/**
	 * 随机角色标识，用于避免返回空集合
	 */
	private final Collection<ConfigAttribute> RANDOM = SecurityConfig.createList("ROLE_" + UUID.randomUUID()
			.toString()
			+ Instant.now()
					.toEpochMilli());

	public ConfigAttributeServiceImpl(final AuthPermissionRepository authPermissionRepository,
			final AuthRoleRepository authRoleRepository, final TenantProperties tenantProperties) {
		this.authPermissionRepository = authPermissionRepository;
		this.authRoleRepository = authRoleRepository;
		this.tenantProperties = tenantProperties;
	}

	@Override
	public Collection<ConfigAttribute> findByFilterInvocation(final FilterInvocation filterInvocation) {
		// 是否忽略
		final boolean ignored = authPermissionRepository.findAll(tenantProperties.getServiceId())
				.parallelStream()
				.filter(p -> Boolean.TRUE.equals(p.getIgnored()))
				.anyMatch(p -> isUrlAllowed(p, filterInvocation));
		if (ignored) {
			// 如果忽略，则返回空属性列表
			return SecurityConfig.createList();
		}

		// 请求的URL匹配的权限ID列表
		final Set<String> urlPermissionIds = authPermissionRepository.findAll(tenantProperties.getServiceId())
				.parallelStream()
				.filter(permission -> isUrlAllowed(permission, filterInvocation))
				.map(AuthPermission::getId)
				.collect(Collectors.toSet());
		if (urlPermissionIds == null || urlPermissionIds.isEmpty()) {
			return handleAbsentUrl();
		}

		// 权限对应的角色
		final List<AuthRole> roles = authRoleRepository.findByPermissionIds(urlPermissionIds);
		if (roles == null || roles.isEmpty()) {
			return handleAbsentUrl();
		}

		// 角色标识集合列表
		final String[] attrs = roles.parallelStream()
				.map(AuthRole::getIdentifier)
				.collect(Collectors.toSet())
				.toArray(new String[0]);
		return attrs.length == 0 ? handleAbsentUrl() : SecurityConfig.createList(attrs);
	}

	/**
	 * 处理未关联权限的URL。
	 *
	 * @return 未关联权限的Attributes
	 */
	private Collection<ConfigAttribute> handleAbsentUrl() {
		return RANDOM;
	}

	private boolean isUrlAllowed(final AuthPermission permission, final FilterInvocation filterInvocation) {
		// 检查servletPath + pathInfo
		final AntPathRequestMatcher matcher = new AntPathRequestMatcher(permission.getUrl());
		final boolean matches = matcher.matches(filterInvocation.getHttpRequest());
		if (!matches) {
			return false;
		}

		// 检查HTTP method
		final String permissionMethod = StringUtils.trimToEmpty(permission.getMethod());
		if (ANY_METHOD.equals(permissionMethod)) {
			// *表示任意HTTP method
			return true;
		}

		final String httpMethod = filterInvocation.getHttpRequest()
				.getMethod();
		final String[] methods = StringUtils.split(permissionMethod, ",");
		for (final String method : methods) {
			if (method.strip()
					.equalsIgnoreCase(httpMethod)) {
				return true;
			}
		}

		return false;
	}

}
