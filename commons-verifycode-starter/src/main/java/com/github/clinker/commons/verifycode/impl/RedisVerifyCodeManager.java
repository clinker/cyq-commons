package com.github.clinker.commons.verifycode.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.github.clinker.commons.util.exception.ServiceException;
import com.github.clinker.commons.verifycode.VerifyCodeError;
import com.github.clinker.commons.verifycode.VerifyCodeManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证码用Redis管理。
 * <p/>
 * <ul>
 * 特性：
 * <li>每个键（例如：手机号）在一段时间内最多发一次，默认60秒内；</li>
 * <li>验证码最多使用若干次，然后作废，默认3次。</li>
 * </ul>
 */
@Slf4j
public class RedisVerifyCodeManager implements VerifyCodeManager {

	/**
	 * epoch时间戳
	 */
	private final String HASH_KEY_TIMESTAMP = "ts";

	/**
	 * 使用次数
	 */
	private final String HASH_KEY_USED = "used";

	/**
	 * 验证码
	 */
	private final String HASH_KEY_VALUE = "val";

	/**
	 * 同一个key下，两次新验证码之间时间间隔，单位：毫秒
	 */
	private final long intervalInMills;

	/**
	 * key前缀
	 */
	private final String keyPrefix;

	/**
	 * 做多被使用次数
	 */
	private final int maxUsed;

	private final StringRedisTemplate redisTemplate;

	/**
	 * 超时时长
	 */
	private final Duration timeout;

	public RedisVerifyCodeManager(final StringRedisTemplate redisTemplate, final long intervalInMills,
			final String keyPrefix, final int maxUsed, final Duration timeout) {
		this.redisTemplate = redisTemplate;
		this.intervalInMills = intervalInMills;
		this.keyPrefix = keyPrefix;
		this.maxUsed = maxUsed;
		this.timeout = timeout;
	}

	@Override
	public void create(final String key, final String verifyCode) {
		final String redisKey = redisKey(key);
		final long nowMilli = now();

		final Object ts = redisTemplate.opsForHash()
				.get(redisKey, HASH_KEY_TIMESTAMP);
		if (ts != null) {
			// 是否在间隔期以内
			final long tsMilli = Long.parseLong(String.valueOf(ts));

			if (nowMilli - tsMilli < intervalInMills) {
				throw new ServiceException(VerifyCodeError.TOO_FREQUENT, "请求过于频繁");
			}
		}

		// 新建，或覆盖现有
		redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
			final StringRedisConnection stringRedisConn = (StringRedisConnection) connection;

			final Map<String, String> hashes = Map.of(
					//
					HASH_KEY_VALUE, verifyCode,
					//
					HASH_KEY_TIMESTAMP, String.valueOf(nowMilli),
					// 未曾使用过
					HASH_KEY_USED, "0");

			stringRedisConn.hMSet(redisKey, hashes);// 设置hash值
			stringRedisConn.expire(redisKey, timeout.toSeconds());// 设置过期

			return null;
		});
	}

	@Override
	public void delete(final String key) {
		redisTemplate.delete(redisKey(key));
	}

	@Override
	public boolean matches(final String key, final String userInputVerifyCode) {
		final String redisKey = redisKey(key);

		final List<Object> list = redisTemplate.opsForHash()
				.multiGet(redisKey, List.of(HASH_KEY_VALUE, HASH_KEY_USED));
		if (list == null || list.isEmpty()) {
			return false;
		}

		final String verifyCode = (String) list.get(0);
		if (verifyCode == null || verifyCode.isBlank()) {
			return false;
		}
		if (userInputVerifyCode != null && userInputVerifyCode.equalsIgnoreCase(verifyCode)) {
			delete(key);
			return true;
		}

		if (list.get(1) == null) {// usded
			return false;
		}
		final int used = Integer.parseInt((String) list.get(1));
		if (used >= maxUsed - 1) {
			log.debug("Exceed max used: {}", redisKey);
			delete(key);
		} else {
			// 使用次数递增
			redisTemplate.opsForHash()
					.increment(redisKey, HASH_KEY_USED, 1);
		}
		return false;
	}

	private long now() {
		return Instant.now()
				.toEpochMilli();
	}

	private String redisKey(final String key) {
		return keyPrefix + key;
	}

}
