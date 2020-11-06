package com.github.clinker.commons.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 租户属性。
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class TenantProperties {

	/**
	 * 所属服务ID，即租户
	 */
	private String serviceId;

}
