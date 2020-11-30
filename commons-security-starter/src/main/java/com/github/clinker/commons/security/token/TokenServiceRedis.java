package com.github.clinker.commons.security.token;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Redis实现的token服务。
 * <p/>
 * 每个登录存两条：
 * <p>
 * 一个value，存放token，key是${auth.token}:${token}，值是TokenValue
 * </p>
 * <p>
 * 一个set，存放account的所有token，key是${auth.token}:accounts:${accountId}，值是token的key
 * </p>
 *
 */
public class TokenServiceRedis implements TokenService {

	private final ObjectMapper objectMapper;

	private final StringRedisTemplate stringRedisTemplate;

	private final TokenGenerator tokenGenerator;

	private final TokenProperties tokenProperties;

	public TokenServiceRedis(final ObjectMapper objectMapper, final StringRedisTemplate stringRedisTemplate,
			final TokenGenerator tokenGenerator, final TokenProperties tokenProperties) {
		this.objectMapper = objectMapper;
		this.stringRedisTemplate = stringRedisTemplate;
		this.tokenGenerator = tokenGenerator;
		this.tokenProperties = tokenProperties;
	}

	/**
	 * AccountId集合的key。
	 *
	 * @param accountId 账号ID
	 * @return account ID集合t的key
	 */
	private String accountKey(final String accountId) {
		if (StringUtils.isBlank(accountId)) {
			return null;
		}

		final String suffix = "accounts:" + accountId;
		final String prefix = tokenProperties.getPrefix();

		return StringUtils.isBlank(prefix) ? suffix : prefix + ":" + suffix;
	}

	@Override
	public String create(final String accountId, final TokenValue value) {
		final String token = tokenGenerator.generate(accountId);

		final String tokenKey = tokenKey(token);

		String json;
		try {
			json = objectMapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
			final StringRedisConnection conn = (StringRedisConnection) connection;

			// 存token的值
			conn.set(tokenKey, json, Expiration.from(tokenProperties.getTimeout()), SetOption.upsert());

			// 存account关联的token
			final String accountKey = accountKey(accountId);
			conn.sAdd(accountKey, tokenKey);
			// account key过期时间
			conn.expire(accountKey, tokenProperties.getTimeout()
					.toSeconds());

			return null;
		});

		return token;
	}

	@Override
	public void deleteByAccountId(final String accountId) {
		if (StringUtils.isBlank(accountId)) {
			return;
		}

		final String accountKey = accountKey(accountId);

		// 删除tokens
		final Set<String> tokenKeys = findTokenKeysByAccountId(accountId);
		if (tokenKeys != null && !tokenKeys.isEmpty()) {
			stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
				final StringRedisConnection conn = (StringRedisConnection) connection;
				conn.del(tokenKeys.toArray(new String[] {}));
				return null;
			});
		}

		// 删除account
		stringRedisTemplate.delete(accountKey);
	}

	@Override
	public void deleteByToken(final String token) {
		if (StringUtils.isBlank(token)) {
			return;
		}

		final String tokenKey = tokenKey(token);

		final TokenValue tokenValue = findByToken(token);

		if (tokenValue != null) {
			// 从account里删除
			final String accountKey = accountKey(tokenValue.getAccountId());
			stringRedisTemplate.opsForSet()
					.remove(accountKey, tokenKey);
		}

		stringRedisTemplate.delete(tokenKey);
	}

	@Override
	public void extend(final String token) {
		if (StringUtils.isBlank(token)) {
			return;
		}

		final String tokenKey = tokenKey(token);

		final TokenValue tokenValue = findByToken(token);
		if (tokenValue == null) {
			return;
		}
		final String accountKey = accountKey(tokenValue.getAccountId());

		final long timeout = tokenProperties.getTimeout()
				.toSeconds();
		final long ttl = stringRedisTemplate.getExpire(tokenKey);

		if (ttl < timeout / 2 && ttl > 0) {
			// 过期时间超过了超时的一半，则延期
			stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
				final StringRedisConnection conn = (StringRedisConnection) connection;
				conn.expire(tokenKey, tokenProperties.getTimeout()
						.toSeconds());
				conn.expire(accountKey, tokenProperties.getTimeout()
						.toSeconds());
				return null;
			});
		}
	}

	@Override
	public TokenValue findByHeaderValue(final String tokenHeaderValue) {
		if (StringUtils.isBlank(tokenHeaderValue)) {
			return null;
		}

		final String token = StringUtils.removeStartIgnoreCase(tokenHeaderValue, tokenProperties.getHeaderValuePrefix())
				.trim();
		return findByToken(token);
	}

	@Override
	public TokenValue findByToken(final String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}

		final String json = stringRedisTemplate.opsForValue()
				.get(tokenKey(token));
		if (StringUtils.isNotBlank(json)) {
			try {
				return objectMapper.readValue(json, TokenValue.class);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}

		return null;
	}

	@Override
	public Set<String> findTokenKeysByAccountId(final String accountId) {
		if (StringUtils.isBlank(accountId)) {
			return Set.of();
		}

		final String accountKey = accountKey(accountId);

		final Set<String> tokenKeys = stringRedisTemplate.opsForSet()
				.members(accountKey);

		return tokenKeys == null ? Set.of() : tokenKeys;
	}

	@Override
	public String parseToken(final String tokenHeaderValue) {
		return StringUtils.removeStartIgnoreCase(tokenHeaderValue, tokenProperties.getHeaderValuePrefix())
				.trim();
	}

	/**
	 * Token在redis里的key。
	 *
	 * @param token token
	 * @return token key
	 */
	private String tokenKey(final String token) {
		final String prefix = tokenProperties.getPrefix();

		return StringUtils.isBlank(prefix) ? token : prefix + ":" + token;
	}

}
