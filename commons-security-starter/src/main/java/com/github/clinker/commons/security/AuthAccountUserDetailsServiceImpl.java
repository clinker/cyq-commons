package com.github.clinker.commons.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.clinker.commons.security.entity.AuthAccount;
import com.github.clinker.commons.security.entity.AuthRole;
import com.github.clinker.commons.security.repository.AuthAccountRepository;
import com.github.clinker.commons.security.repository.AuthRoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 实现{@link UserDetailsService}。
 */
@Slf4j
public class AuthAccountUserDetailsServiceImpl implements UserDetailsService {

	private final AuthAccountRepository authAccountRepository;

	private final AuthRoleRepository authRoleRepository;

	public AuthAccountUserDetailsServiceImpl(final AuthAccountRepository authAccountRepository,
			final AuthRoleRepository authRoleRepository) {
		this.authAccountRepository = authAccountRepository;
		this.authRoleRepository = authRoleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		if (StringUtils.isBlank(username)) {
			log.warn("Username is empty");
			throw new UsernameNotFoundException("账号不存在：" + username);
		}

		final AuthAccount account = authAccountRepository.findByUsername(username);
		if (account == null || Boolean.TRUE.equals(account.getDeleted())) {
			log.warn("Username not found: {}", username);
			throw new UsernameNotFoundException("账号不存在：" + username);
		}

		// 角色标识作为GrantedAuthority
		final Set<GrantedAuthority> grantedAuthorities = authRoleRepository.findByAccountId(account.getId())
				.parallelStream()
				.map(AuthRole::getIdentifier)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());

		return new AuthAccountUserDetails(account.getId(), account.getUsername(), account.getPassword(),
				grantedAuthorities);
	}

}
