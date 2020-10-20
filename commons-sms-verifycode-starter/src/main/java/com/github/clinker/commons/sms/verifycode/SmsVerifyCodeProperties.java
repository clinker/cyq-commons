package com.github.clinker.commons.sms.verifycode;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 配置属性。
 */
@Data
@ConfigurationProperties(prefix = "sms.verifycode")
public class SmsVerifyCodeProperties {

	/**
	 * 验证码长度
	 */
	private int length = 4;

	/**
	 * 验证码字符
	 */
	private String source = "0123456789";

	/**
	 * key前缀
	 */
	private String keyPrefix = "vc:";

	/**
	 * 超时时长
	 */
	private Duration timeout = Duration.ofMinutes(2);

	/**
	 * 做多被使用次数
	 */
	private int maxUsed = 3;

	/**
	 * 同一个key下，两次新验证码之间时间间隔，单位：毫秒
	 */
	private long intervalInMills = 60 * 1000;

}
