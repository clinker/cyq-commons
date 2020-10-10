package com.github.clinker.commons.security.token;

/**
 * 认证token生成器接口。
 */
public interface TokenGenerator {

	String generate(Object principal);

}
