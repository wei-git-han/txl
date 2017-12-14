package com.css.base.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.css.base.filter.SSOAuthFilter;

/**
 * 跨域请求数据
 * @author mashuwen
 * @date 2017-10-12 21:17:28
 */
public class CrossDomainUtil {

	/**
	 * 跨域post请求
	 * @param url 请求地址
	 * @param map 请求参数集合
	 * @return 返回JSON数据
	 */
	public static JSONObject getJsonData(String url,LinkedMultiValueMap<String, Object> map){
		//设置消息头
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
		headers.setContentType(mediaType);
		//设置请求参数
        HttpEntity<LinkedMultiValueMap<String, Object>> formEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
        //创建RestTemplate对象
        RestTemplate restTemplate = new RestTemplate();
        url+="?access_token=" + SSOAuthFilter.getToken();
        //发送post请求
        ResponseEntity<JSONObject> data = restTemplate.postForEntity(url, formEntity, JSONObject.class);
        //返回请求数据
		return data.getBody();
	}

}
