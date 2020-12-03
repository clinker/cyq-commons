package com.github.clinker.commons.jackson;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.github.clinker.commons.util.time.DateTimeFormatters;

/**
 * Jackson自动配置。
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class CyqJacksonAutoConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			builder.locale(Locale.CHINA);
			builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			builder.simpleDateFormat(DateTimeFormatters.DATE_TIME_PATTERN);
			builder.serializers(new LocalTimeSerializer(DateTimeFormatters.TIME));
			builder.serializers(new LocalDateSerializer(DateTimeFormatters.DATE));
			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatters.DATE_TIME));

			// uni-app的JSON请求的key没有引号
			builder.featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
		};
	}

}
