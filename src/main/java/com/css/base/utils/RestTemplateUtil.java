package com.css.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate请求工具类
 * 
 * @author gengds
 *
 */
public class RestTemplateUtil {

	// 日志记录
	private static Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

	/**
	 * post请求，返回json字符串
	 * 
	 * @param url 请求地址
	 * @param map 请求参数
	 * @return
	 */
	public static String postJSONString(String url, LinkedMultiValueMap<String, Object> map) {
		// 设置消息头
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
		headers.setContentType(mediaType);
		// 设置请求参数
		HttpEntity<LinkedMultiValueMap<String, Object>> formEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);
		// 创建RestTemplate对象
		RestTemplate restTemplate = new RestTemplate();
		logger.info("请求路径:{}", url);
		ResponseEntity<String> result = restTemplate.postForEntity(url, formEntity, String.class);
		logger.info("返回结果:{}", result.getBody());
		return result.getBody();
	}

}
