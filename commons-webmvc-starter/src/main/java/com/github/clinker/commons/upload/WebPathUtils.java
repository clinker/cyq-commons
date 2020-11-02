package com.github.clinker.commons.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;

public class WebPathUtils {

	private static final String SLASH = "/";

	/**
	 * 在webPath后追加filename。如果webPath为空，则使用请求的scheme + host + port。
	 *
	 * @param request  请求
	 * @param webPath  文件的web路径
	 * @param filename 文件名
	 * @return 文件的完整URI
	 */
	public static String getFileUri(final HttpServletRequest request, final String webPath, final String filename) {
		final String unixStyleFilename = FilenameUtils.separatorsToUnix(filename);
		String uriPrefix = webPath;

		final StringBuilder uri = new StringBuilder();

		if (uriPrefix == null || uriPrefix.trim().isEmpty()) {
			final StringBuilder requestUriPrefix = new StringBuilder(50);

			requestUriPrefix.append(request.getScheme()).append("://").append(request.getServerName());
			if (request.getServerPort() != 80) {
				requestUriPrefix.append(":").append(request.getServerPort());
			}
			requestUriPrefix.append(request.getContextPath());

			uriPrefix = requestUriPrefix.toString();
		}

		uri.append(uriPrefix);

		if (!(uriPrefix.endsWith(SLASH) || unixStyleFilename.startsWith(SLASH))) {
			uri.append(SLASH);
		}
		if (uriPrefix.endsWith(SLASH) && unixStyleFilename.startsWith(SLASH)) {
			uri.append(unixStyleFilename.substring(1));
		} else {
			uri.append(unixStyleFilename);
		}

		return uri.toString();
	}

	/**
	 * 组装上传文件的完整URI。
	 *
	 * @param uriPrefix 上传webapp的URI，例如：https://localhost/upload
	 * @param webPath   相对于本地存储路径的目录和文件名，例如：2017\3\e07b88282ce5cd2e44c751b4b4944df9.jpg。如果已经是完整路径，则返回该路径
	 *
	 * @return 上传文件的完整URI，例如：https://localhost/upload/2017/3/e07b88282ce5cd2e44c751b4b4944df9.jpg
	 */
	public static String toFullUri(final String uriPrefix, final String webPath) {
		if (uriPrefix == null || uriPrefix.trim().isEmpty()) {
			return "";
		}
		if (webPath == null || webPath.trim().isEmpty()) {
			return "";
		}

		if (webPath.startsWith("https://") || webPath.startsWith("http://")) {
			return webPath;
		}

		final StringBuilder uri = new StringBuilder();

		uri.append(uriPrefix);

		if (!(uriPrefix.endsWith(SLASH) || webPath.startsWith(SLASH))) {
			uri.append(SLASH);
		}
		if (uriPrefix.endsWith(SLASH) && webPath.startsWith(SLASH)) {
			uri.append(webPath.substring(1));
		} else {
			uri.append(webPath);
		}

		return FilenameUtils.separatorsToUnix(uri.toString());
	}

	private WebPathUtils() {

	}

}
