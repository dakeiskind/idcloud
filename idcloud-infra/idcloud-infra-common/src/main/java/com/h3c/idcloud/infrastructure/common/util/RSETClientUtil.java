package com.h3c.idcloud.infrastructure.common.util;

import com.google.common.base.Throwables;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Rset client util 类.
 *
 * @author zharong
 */
public class RSETClientUtil {

	/**
	 * 静态变量 logger.
	 */
	private static Log logger = LogFactory.getLog(RSETClientUtil.class);

	/**
	 * 静态变量 startTime.
	 */
	private static long startTime = 0L;
	/**
	 * 静态变量 endTime.
	 */
	private static long endTime = 0L;


	/**
	 * POST调用WebService方法
	 *
	 * @param url       WebService地址
	 * @param paramters JSON格式的参数
	 * @return 调用结果 rest http response
	 * @throws Exception the exception
	 */
	public static RESTHttpResponse post(String url, String paramters) throws Exception {
		return post(url, paramters, null);
	}

	/**
	 * POST调用WebService方法
	 *
	 * @param url       WebService地址
	 * @param paramters JSON格式的参数
	 * @param headers   请求头
	 * @return 调用结果 rest http response
	 * @throws Exception the exception
	 */
	public static RESTHttpResponse post(String url, String paramters, Map<String, String> headers) throws Exception {

		RESTHttpResponse result = new RESTHttpResponse();

		try {
			logger.debug("调用WebService接口开始。Url：" + url);

			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(url);
			Response response;
			if (headers != null) {
				MultivaluedHashMap<String, Object> headerList = new MultivaluedHashMap<>(headers);
				response = target.request().headers(headerList).post(Entity.json(paramters));
			} else {
				response = target.request().post(Entity.json(paramters));
			}
			// 获取返回内容
			String content = response.readEntity(String.class);
			// 设置返回状态码
			int statusCode = response.getStatus();

			result.setStatus(statusCode);
			result.setContent(content);

			logger.debug("调用WebService接口结束。响应状态：" + statusCode + "，响应内容" + content);
		} catch (Exception ex) {
			// 发生网络异常
			logger.error(Throwables.getStackTraceAsString(ex));
			throw ex;
		}

		return result;
	}

	/**
	 * get调用WebService方法
	 *
	 * @param url WebService地址
	 * @return 调用结果 rest http response
	 * @throws Exception the exception
	 */
	public static RESTHttpResponse get(String url) throws Exception {
		return get(url, null);
	}

	/**
	 * get调用WebService方法
	 *
	 * @param url     WebService地址
	 * @param headers 请求头
	 * @return 调用结果 rest http response
	 * @throws Exception the exception
	 */
	public static RESTHttpResponse get(String url, Map<String, String> headers) throws Exception {

		RESTHttpResponse result = new RESTHttpResponse();

		try {
			logger.debug("调用WebService接口开始。Url：" + url);

			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(url);
			Response response;
			if (headers != null) {
				MultivaluedHashMap<String, Object> headerList = new MultivaluedHashMap<>(headers);
				response = target.request()
						.headers(headerList)
						.header("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8")
						.get();
			} else {
				response = target.request()
						.header("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8").get();
			}
			// 获取返回内容
			String content = response.readEntity(String.class);
			// 设置返回状态码
			int statusCode = response.getStatus();
			logger.info("statusCode:" + statusCode);

			result.setStatus(statusCode);
			result.setContent(content);
			logger.debug("Response Infromation:" + content);

		} catch (Exception e) {
			// 发生网络异常
			logger.error("Exception occurred!\n" + Throwables.getStackTraceAsString(e));
			// 网络错误
			throw e;
		}

		return result;
	}

	/**
	 * delete调用WebService方法
	 *
	 * @param url WebService地址
	 * @return 调用结果 rest http response
	 */
	public static RESTHttpResponse delete(String url) {

		RESTHttpResponse result = new RESTHttpResponse();

		try {
			logger.debug("调用WebService接口开始。Url：" + url);

			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(url);
			Response response;
			response = target.request()
						.header("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8").delete();
			// 获取返回内容
			String content = response.readEntity(String.class);
			// 设置返回状态码
			int statusCode = response.getStatus();
			logger.info("statusCode:" + statusCode);

			result.setStatus(statusCode);
			result.setContent(content);
			logger.debug("Response Infromation:" + content);
		} catch (Exception e) {
			// 发生网络异常
			logger.error("Exception occurred!\n" + Throwables.getStackTraceAsString(e));
			// 网络错误
		}
		return result;
	}

	/**
	 * POST以form参数格式调用WebService方法
	 *
	 * @param url        WebService地址
	 * @param formparams the formparams
	 * @return 调用结果 rest http response
	 * @throws Exception the exception
	 */
	public static RESTHttpResponse post(String url, List<BasicNameValuePair> formparams) throws Exception {

		RESTHttpResponse result = new RESTHttpResponse();

		try {
			logger.debug("调用WebService接口开始。Url：" + url);

			HttpPost httpPost = new HttpPost(url);
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);
			httpPost.setEntity(uefEntity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(httpPost);
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			// 设置返回状态码
			result.setStatus(statusCode);

			// 获取返回内容
			InputStream ins = response.getEntity().getContent();
			StringWriter writer = new StringWriter();
			IOUtils.copy(ins, writer, "UTF-8");
			result.setContent(writer.toString());

			logger.debug("调用WebService接口结束。响应状态：" + statusCode + "，响应内容" + writer.toString());
		} catch (Exception ex) {
			// 发生网络异常
			logger.error(ExceptionUtils.getStackTrace(ex));
			throw ex;
		}

		return result;
	}

	/**
	 * 获得 start time.
	 *
	 * @return the startTime
	 */
	public long getStartTime() {

		return startTime;
	}

	/**
	 * 获得 end time.
	 *
	 * @return the endTime
	 */
	public long getEndTime() {

		return endTime;
	}
}
