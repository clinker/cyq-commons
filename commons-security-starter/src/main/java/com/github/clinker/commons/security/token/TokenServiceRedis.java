package com.github.clinker.commons.security.token;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Redis实现的token服务。
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

	@Override
	public String create(final Object principal, final TokenValue value) {
		final String token = tokenGenerator.generate(principal);

		final String key = key(token);

		String json;
		try {
			json = objectMapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		stringRedisTemplate.opsForValue()
				.set(key, json, tokenProperties.getTimeout());

		return token;
	}

	@Override
	public void delete(final String token) {
		stringRedisTemplate.delete(key(token));
	}

	@Override
	public void extend(final String token) {
		final String key = key(token);

		final long timeout = tokenProperties.getTimeout()
				.toSeconds();
		final long ttl = stringRedisTemplate.getExpire(key);

		if (ttl < timeout / 2 && ttl > 0) {
			// 过期时间超过了超时的一半，则延期
			stringRedisTemplate.expire(key(token), tokenProperties.getTimeout());
		}
	}

	@Override
	public TokenValue findByToken(final String token) {
		final String json = stringRedisTemplate.opsForValue()
				.get(key(token));
		if (StringUtils.isNotBlank(json)) {
			try {
				return objectMapper.readValue(json, TokenValue.class);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}

	/**
	 * redis key。
	 *
	 * @param token token
	 * @return token或加前缀
	 */
	private String key(final String token) {
		final String prefix = tokenProperties.getPrefix();
		if (StringUtils.isNotBlank(prefix)) {
			return prefix + ":" + token;
		} else {
			return token;
		}
	}

}
