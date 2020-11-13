package com.github.clinker.commons.security.repository.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.github.clinker.commons.security.entity.AuthAccount;
import com.github.clinker.commons.security.repository.AuthAccountRepository;

/**
 * 账号仓库实现。
 */
public class AuthAccountRepositoryImpl implements AuthAccountRepository {

	private final RowMapper<AuthAccount> authAccountMapper = (rs, rowNum) -> {
		int index = 1;

		final AuthAccount account = new AuthAccount();
		account.setId(rs.getString(index++));
		account.setServiceId(rs.getString(index++));
		account.setUsername(rs.getString(index++));
		account.setPassword(rs.getString(index++));
		account.setDisabled(rs.getBoolean(index++));
		account.setAvatar(rs.getString(index++));
		account.setDescription(rs.getString(index++));
		account.setCreationTime(rs.getTimestamp(index++)
				.toLocalDateTime());
		account.setModifiedTime(rs.getTimestamp(index++)
				.toLocalDateTime());
		account.setDeleted(rs.getBoolean(index++));

		return account;
	};

	private final JdbcTemplate jdbcTemplate;

	public AuthAccountRepositoryImpl(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public AuthAccount findByUsername(final String username, final String serviceId) {
		final String sql = "SELECT id,service_id,username,password,disabled,avatar,description,creation_time,modified_time,deleted FROM auth_account WHERE service_id=? AND username=? AND deleted=FALSE";

		final List<AuthAccount> accounts = jdbcTemplate.query(sql, authAccountMapper, username, serviceId);

		return accounts == null || accounts.isEmpty() ? null : accounts.get(0);
	}

}
