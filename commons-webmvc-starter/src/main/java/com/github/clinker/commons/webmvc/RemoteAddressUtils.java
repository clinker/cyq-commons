package com.github.clinker.commons.webmvc;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取HTTP远程地址。
 */
public class RemoteAddressUtils {

	private static final Logger log = LoggerFactory.getLogger(RemoteAddressUtils.class);

	private static final String LOOPBACK_IPV6 = "::1";

	private static final String LOOPBACK = "127.0.0.1";

	private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";

	private static final String UNKNOWN = "unknown";

	private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

	private static final String X_FORWARDED_FOR = "x-forwarded-for";

	private static boolean isInvalid(final String ipAddress) {
		return ipAddress == null || ipAddress.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddress);
	}

	/**
	 * 获取HTTP请求的客户端地址。
	 *
	 * @param request HttpServletRequest
	 * @return HTTP请求的客户端地址
	 */
	public static String resolve(final HttpServletRequest request) {
		String ipAddress = request.getHeader(X_FORWARDED_FOR);

		if (isInvalid(ipAddress)) {
			ipAddress = request.getHeader(PROXY_CLIENT_IP);
		}

		if (isInvalid(ipAddress)) {
			ipAddress = request.getHeader(WL_PROXY_CLIENT_IP);
		}

		if (isInvalid(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals(LOOPBACK) || ipAddress.equals(LOOPBACK_IPV6)) {
				// 根据网卡取本机配置的IP
				try {
					final InetAddress inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (final UnknownHostException e) {
					log.error("Get local IP error: {}", e.getMessage());
				}
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	private RemoteAddressUtils() {

	}
}
