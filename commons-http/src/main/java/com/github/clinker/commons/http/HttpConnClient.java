package com.github.clinker.commons.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.io.CloseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 使用Apache HttpComponents Client实现。
 * <p/>
 * 特点：
 * <ul>
 * <li>使用连接池；</li>
 * <li>并发连接数量可控；</li>
 * <li>HTTP层出错后，例如IO，抛出HttpException；</li>
 * <li>HTTP状态码不是200时，抛出HttpStatusException。</li>
 * </ul>
 * <p/>
 * 使用方法：
 *
 * <pre>
 *  HttpConn httpConn = new HttpConnClient(5,100);
 *  httpConn.post(...);
 *  httpConn.get(...);
 *  ...
 *  httpConn.close();
 * </pre>
 */
public class HttpConnClient implements HttpConn {

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private final OkHttpClient client = new OkHttpClient();

	private final Logger log = LoggerFactory.getLogger(HttpConnClient.class);

	@Override
	public void close() {
		if (httpClient != null) {
			httpClient.close(CloseMode.IMMEDIATE);
		}

		if (cm != null) {
			cm.close(CloseMode.IMMEDIATE);
		}
	}

	@Override
	public String get(final String url) {
		return get(url, null, null);
	}

	@Override
	public String get(final String url, final Map<String, Object> headers) {
		final Request request = new Request.Builder().url(url);
		return get(url, headers, null);
	}

	@Override
	public String get(final String url, final Map<String, String> headers, final HttpTimeout timeout) {
		log.debug("Get url: {}", url);
		final Request.Builder requestBuilder = new Request.Builder().url(url);
		if (headers != null) {
			headers.entrySet()
					.forEach(entry -> requestBuilder.addHeader(entry.getKey(), entry.getValue()));
		}
		if( timeout!=null) {
			if(timeout.getConnect()>0) {
				requestBuilder
			}
		}
		final Request request = requestBuilder.build();

		String responseString = null;

		try (Response response = client.newCall(request)
				.execute()) {
			responseString = response.body()
					.string();
		} catch (final IOException e) {
			log.error("Http get error", e);

			throw new HttpIoException(responseString, e);
		}

		int httpStatus = HttpStatus.SC_OK;
		final HttpGet get = new HttpGet(url);
		if (requestConfig != null) {
			get.setConfig(requestConfig);
		}

		try (final CloseableHttpResponse response = httpClient.execute(get)) {
			httpStatus = response.getCode();
			responseString = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
			EntityUtils.consume(response.getEntity());
		} catch (final IOException | ParseException e) {
			log.error("Http get error", e);

			throw new HttpIoException(responseString, e);
		}

		if (httpStatus != HttpStatus.SC_OK) {
			log.error("Get not OK, url: {}\nstatus: {}, {}", url, httpStatus, responseString);
			throw new HttpStatusException(httpStatus, responseString);
		}

		log.debug("Get url {}, response: {}", url, responseString);
		return responseString;
	}

	@Override
	public String post(final String url, final HttpEntity entity, final Map<String, Object> headers) {
		return post(url, entity, headers, null);
	}

	@Override
	public String post(final String url, final HttpEntity entity, final Map<String, Object> headers,
			final RequestConfig requestConfig) {
		log.debug("Post url: {}", url);

		String responseString = null;

		int httpStatus = HttpStatus.SC_OK;
		final HttpPost post = new HttpPost(url);
		if (requestConfig != null) {
			post.setConfig(requestConfig);
		}
		post.setEntity(entity);
		if (headers != null) {
			headers.entrySet()
					.forEach(entry -> post.setHeader(entry.getKey(), entry.getValue()));
		}

		try (CloseableHttpResponse response = httpClient.execute(post)) {
			httpStatus = response.getCode();
			responseString = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
			EntityUtils.consume(response.getEntity());
		} catch (final IOException | ParseException e) {
			log.error("Http post error", e);

			throw new HttpIoException(responseString, e);
		}

		if (httpStatus != HttpStatus.SC_OK) {
			log.error("POST not OK, url: {}\nstatus: {}, {}", url, httpStatus, responseString);
			throw new HttpStatusException(httpStatus, responseString);
		}

		log.debug("Post url {}, response: {}", url, responseString);
		return responseString;
	}

	@Override
	public String post(final String url, final String body) {
		return post(url, body, null);
	}

	@Override
	public String post(final String url, final String body, final Map<String, Object> headers) {
		return post(url, body, headers);
	}

	@Override
	public String post(final String url, final String body, final Map<String, Object> headers,
			final RequestConfig requestConfig) {
		return post(url, new StringEntity(body, DEFAULT_CHARSET), headers, requestConfig);
	}

}
