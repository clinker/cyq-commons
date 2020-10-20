package com.github.clinker.commons.sms.verifycode.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.github.clinker.commons.sms.verifycode.SmsVerifyCodeGenerator;
import com.github.clinker.commons.sms.verifycode.SmsVerifyCodeManager;
import com.github.clinker.commons.sms.verifycode.SmsVerifyCodeProperties;
import com.github.clinker.commons.sms.verifycode.impl.RandomSmsVerifyCodeGenerator;
import com.github.clinker.commons.sms.verifycode.impl.RedisSmsVerifyCodeManager;

import lombok.AllArgsConstructor;

/**
 * 短信验证码自动配置。
 */
@Configuration
@AutoConfigureAfter({ RedisAutoConfiguration.class })
@EnableConfigurationProperties(SmsVerifyCodeProperties.class)
@AllArgsConstructor
public class VerifyCodeAutoConfiguration {

	private final SmsVerifyCodeProperties smsVerifyCodeProperties;

	private final StringRedisTemplate stringRedisTemplate;

	@Bean
	@ConditionalOnMissingBean
	public SmsVerifyCodeGenerator SmsVerifyCodeGenerator() {
		return new RandomSmsVerifyCodeGenerator(smsVerifyCodeProperties.getLength(),
				smsVerifyCodeProperties.getSource());
	}

	@Bean
	@ConditionalOnMissingBean
	public SmsVerifyCodeManager smsVerifyCodeManager() {
		return new RedisSmsVerifyCodeManager(stringRedisTemplate, smsVerifyCodeProperties.getIntervalInMills(),
				smsVerifyCodeProperties.getKeyPrefix(), smsVerifyCodeProperties.getMaxUsed(),
				smsVerifyCodeProperties.getTimeout());
	}

}
