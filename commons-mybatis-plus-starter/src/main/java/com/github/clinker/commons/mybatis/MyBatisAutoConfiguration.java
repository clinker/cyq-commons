package com.github.clinker.commons.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.LocalCacheScope;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * MyBatis Plus自动配置。
 */
@Configuration
@ConditionalOnClass(DataSource.class)
public class MyBatisAutoConfiguration {

	/**
	 * MyBatis Plus配置。
	 */
	@Configuration
	@ConditionalOnClass(MybatisConfiguration.class)
	@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
	static class MybatisPlusConfiguration {

		@Bean
		public ConfigurationCustomizer configurationCustomizer() {
			return configuration -> {
				configuration.setCacheEnabled(false);// 关闭二级缓存
				configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);// 局部缓存改为语句级
			};
		}

		@Bean
		public MybatisPlusInterceptor mybatisPlusInterceptor() {
			final MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

			// 分页插件
			interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

			return interceptor;
		}

	}

}
