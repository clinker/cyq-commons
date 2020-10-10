package com.github.clinker.commons.security.repository;

import java.util.List;

import com.github.clinker.commons.security.entity.AuthPermission;

/**
 * 权限仓库接口。
 */
public interface AuthPermissionRepository {

	/**
	 * 查询服务的全部权限。
	 *
	 * @param serviceId 所属服务ID
	 * @return 服务的全部权限列表
	 */
	List<AuthPermission> findAll(String serviceId);

}
