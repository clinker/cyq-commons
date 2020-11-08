package com.github.clinker.commons.security.token;

import java.util.Set;

/**
 * 认证token服务接口。
 */
public interface TokenService {

	/**
	 * 创建。
	 *
	 * @param accountId 账号ID
	 * @param value     值
	 * @return token
	 */
	String create(String accountId, TokenValue value);

	/**
	 * 删除单个token，并从账号关联的token集合里删除。
	 *
	 * @param token token
	 */
	void delete(String token);

	/**
	 * 删除accountId关联的token集合，并删除其中的所有token。
	 *
	 * @param accountId 账号ID
	 */
	void deleteByAccountId(String accountId);

	/**
	 * 重置超时。
	 *
	 * @param token token
	 */
	void extend(String token);

	/**
	 * 查询值。
	 *
	 * @param token token
	 * @return 值
	 */
	TokenValue findByToken(String token);

	/**
	 * 查询accountId关联的token键。
	 *
	 * @param accountId 账号ID
	 * @return token键集合
	 */
	Set<String> findTokenKeysByAccountId(String accountId);

}
