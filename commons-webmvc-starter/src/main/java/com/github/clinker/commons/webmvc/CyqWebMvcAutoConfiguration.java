package com.github.clinker.commons.webmvc;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.clinker.commons.webmvc.error.ControllerExceptionHandler;
import com.github.clinker.commons.webmvc.filter.RequestDetailLoggingFilter;

/**
 * MVC自动配置。
 */
@Configuration
@ConditionalOnWebApplication
public class CyqWebMvcAutoConfiguration {

	/**
	 * 异常处理自动配置。
	 */
	@ConditionalOnProperty(prefix = PREFIX, name = "error-handling", matchIfMissing = true, havingValue = "true")
	@ComponentScan(basePackageClasses = ControllerExceptionHandler.class)
	static class ErrorConfiguration {

	}

	private static final String PREFIX = "cyq.webmvc";

	/**
	 * 请求详情记录过滤器。
	 *
	 * @return 请求详情记录过滤器
	 */
	@ConditionalOnProperty(prefix = PREFIX, name = "request-logging", matchIfMissing = false, havingValue = "false")
	@Bean
	protected Filter requestDetailLoggingFilter() {
		return new RequestDetailLoggingFilter();
	}

}
