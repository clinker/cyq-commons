package com.github.clinker.commons.validator;

import javax.validation.Validator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证器自动配置。
 */
@Configuration
@ConditionalOnClass(Validator.class)
public class CyqValidatorAutoConfiguration {

	@Configuration
	static class EmbeddedConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public ChinaCellPhoneNumberValidator chinaCellPhoneNumberValidator() {
			return new ChinaCellPhoneNumberValidator();
		}

		@Bean
		@ConditionalOnMissingBean
		public DateTimeValidator dateTimeValidator() {
			return new DateTimeValidator();
		}

		@Bean
		@ConditionalOnMissingBean
		public DateValidator dateValidator() {
			return new DateValidator();
		}
	}

}
