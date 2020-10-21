package com.github.clinker.commons.security.token;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

/**
 * Redis实现的token服务。
 */
@AllArgsConstructor
public class TokenServiceRedis implements TokenService {

	private final ObjectMapper objectMapper;

	private final StringRedisTemplate stringRedisTemplate;

	private final TokenGenerator tokenGenerator;

	private final TokenProperties tokenProperties;

	@Override
	public String create(Object principal, TokenValue value) {
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
	public void delete(String token) {
		stringRedisTemplate.delete(key(token));
	}

	@Override
	public void extend(String token) {
		final String key = key(token);

		final long timeout = tokenProperties.getTimeout()
				.toSeconds();
		final long seconds = stringRedisTemplate.getExpire(key);

		if (seconds > timeout / 2) {
			// 过期时间超过了超时的一半，则延期
			stringRedisTemplate.expire(key(token), tokenProperties.getTimeout());
		}
	}

	@Override
	public TokenValue findByToken(String token) {
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
	private String key(String token) {
		final String prefix = tokenProperties.getPrefix();
		if (StringUtils.isNotBlank(prefix)) {
			return prefix + ":" + token;
		} else {
			return token;
		}
	}

}
