package com.github.clinker.commons.security.repository.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.github.clinker.commons.security.entity.AuthPermission;
import com.github.clinker.commons.security.repository.AuthPermissionRepository;

/**
 * 权限仓库实现。
 */
public class AuthPermissionRepositoryImpl implements AuthPermissionRepository {

	private final RowMapper<AuthPermission> authPermissionMapper = (rs, rowNum) -> {
		int index = 1;

		final AuthPermission permission = new AuthPermission();
		permission.setId(rs.getString(index++));
		permission.setName(rs.getString(index++));
		permission.setUrl(rs.getString(index++));
		permission.setMethod(rs.getString(index++));
		permission.setIgnored(rs.getBoolean(index++));
		permission.setSort(rs.getInt(index++));
		permission.setDescription(rs.getString(index++));

		return permission;
	};

	private final JdbcTemplate jdbcTemplate;

	public AuthPermissionRepositoryImpl(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<AuthPermission> findAll(final String serviceId) {
		final String sql = "SELECT id,name,url,method,ignored,sort,description FROM auth_permission WHERE service_id=?";

		return jdbcTemplate.query(sql, authPermissionMapper, serviceId);
	}

}
