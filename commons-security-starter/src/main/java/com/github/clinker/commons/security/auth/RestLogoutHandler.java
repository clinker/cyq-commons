package com.github.clinker.commons.security.auth;

import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * 注销时删除认证token。
 */
public interface RestLogoutHandler extends LogoutHandler {

}
