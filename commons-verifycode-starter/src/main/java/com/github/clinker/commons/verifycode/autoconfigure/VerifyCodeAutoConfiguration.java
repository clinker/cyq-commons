package com.github.clinker.commons.verifycode.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.github.clinker.commons.verifycode.VerifyCodeGenerator;
import com.github.clinker.commons.verifycode.VerifyCodeManager;
import com.github.clinker.commons.verifycode.VerifyCodeProperties;
import com.github.clinker.commons.verifycode.impl.RandomVerifyCodeGenerator;
import com.github.clinker.commons.verifycode.impl.RedisVerifyCodeManager;

import lombok.AllArgsConstructor;

/**
 * 验证码自动配置。
 */
@Configuration
@AutoConfigureAfter({ RedisAutoConfiguration.class })
@EnableConfigurationProperties(VerifyCodeProperties.class)
@AllArgsConstructor
public class VerifyCodeAutoConfiguration {

	private final StringRedisTemplate stringRedisTemplate;

	private final VerifyCodeProperties verifyCodeProperties;

	@Bean
	@ConditionalOnMissingBean
	public VerifyCodeGenerator verifyCodeGenerator() {
		return new RandomVerifyCodeGenerator(verifyCodeProperties.getLength(), verifyCodeProperties.getSource());
	}

	@Bean
	@ConditionalOnMissingBean
	public VerifyCodeManager verifyCodeManager() {
		return new RedisVerifyCodeManager(stringRedisTemplate, verifyCodeProperties.getIntervalInMills(),
				verifyCodeProperties.getKeyPrefix(), verifyCodeProperties.getMaxUsed(),
				verifyCodeProperties.getTimeout());
	}

}
