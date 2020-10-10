package com.github.clinker.commons.security.repository.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.github.clinker.commons.security.entity.AuthRole;
import com.github.clinker.commons.security.repository.AuthRoleRepository;

/**
 * 角色仓库实现。
 */
public class AuthRoleRepositoryImpl implements AuthRoleRepository {

	private final RowMapper<AuthRole> authRoleMapper = (rs, rowNum) -> {
		int index = 1;

		final AuthRole role = new AuthRole();
		role.setId(rs.getString(index++));
		role.setName(rs.getString(index++));
		role.setIdentifier(rs.getString(index++));
		role.setSuperRole(rs.getBoolean(index++));
		role.setSort(rs.getInt(index++));
		role.setDescription(rs.getString(index++));
		role.setCreatedTime(rs.getTimestamp(index++)
				.toLocalDateTime());

		return role;
	};

	private final JdbcTemplate jdbcTemplate;

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public AuthRoleRepositoryImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<AuthRole> findByAccountId(String accountId) {
		final String sql = "SELECT r.id,r.name,r.identifier,r.super_role,r.sort,r.description,r.created_time FROM auth_role AS r"
				+ " INNER JOIN auth_account_role AS j ON r.id=j.role_id WHERE j.account_id=?";

		return jdbcTemplate.query(sql, new Object[] { accountId }, authRoleMapper);
	}

	@Override
	public List<AuthRole> findByPermissionIds(Collection<String> permissionIds) {
		final String sql = "SELECT r.id,r.name,r.identifier,r.super_role,r.sort,r.description,r.created_time FROM auth_role AS r"
				+ " INNER JOIN auth_role_permission AS rp ON r.id=rp.role_id WHERE rp.permission_id IN(:permissionIds)";

		final Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("permissionIds", permissionIds);

		return namedParameterJdbcTemplate.query(sql, paramMap, authRoleMapper);
	}

	@Override
	public List<AuthRole> findSuper(String serviceId) {
		final String sql = "SELECT id,name,identifier,super_role,sort,description,created_time FROM auth_role WHERE service_id=? AND super_role=TRUE";

		return jdbcTemplate.query(sql, new Object[] { serviceId }, authRoleMapper);
	}

}
