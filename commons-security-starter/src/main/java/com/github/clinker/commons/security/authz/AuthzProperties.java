package com.github.clinker.commons.security.authz;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 授权属性。
 */
@Data
@ConfigurationProperties(prefix = "authz")
public class AuthzProperties {

	/**
	 * 所属服务ID，即租户
	 */
	private String serviceId;

	/**
	 * 授权缓存redis topic
	 */
	private String cacheTopic = "authz:refresh";

}
