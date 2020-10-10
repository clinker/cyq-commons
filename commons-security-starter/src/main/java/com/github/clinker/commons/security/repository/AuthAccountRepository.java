package com.github.clinker.commons.security.repository;

import com.github.clinker.commons.security.entity.AuthAccount;

/**
 * 账号仓库接口。
 */
public interface AuthAccountRepository {

	/**
	 * 根据登录名查询。
	 *
	 * @param username 登录名
	 * @return 账号，或null
	 */
	AuthAccount findByUsername(String username);

}
