package com.css.base.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 返回数据
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017年3月16日16:44:26
 */
public class Response extends ResponseBase {
	public Response() {
	}

	public static void error() {
		error(500, "未知异常，请联系管理员");
	}

	public static void error(String msg) {
		error(500, msg);
	}

	public static void error(int code, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		json(map);
	}

	public static void ok() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "success");
		json(map);
	}

	public static void string(String message) {
		new Response().setContentType("text/html;charset=UTF-8").write(message);
	}

	public static void json(String key, Object val) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, val);
		json(map);
	}

	public static void redirect(String url) {
		new Response().sendRedirect(url);
	}

	public static void json(Object obj) {
		byte[] bytes = JSONObject.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteDateUseDateFormat).getBytes();
		new Response().setContentType("application/json;charset=utf-8").write(bytes);
	}

	/**
	 * 下载大文件使用
	 * 
	 * @param filename
	 * @param is
	 */
	public static void download(String filename, InputStream is) {
		try {
			String fileName = new String((filename).getBytes("UTF-8"), "iso-8859-1");
			new Response().setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName))
					.write(is);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 下载文件
	 * 
	 * @param filename
	 * @param file
	 */
	public static void download(String filename, File file) {
		try {
			download(filename, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载小文件使用
	 * 
	 * @param filename
	 * @param bytes
	 */
	public static void download(String filename, byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		download(filename, bais);
	}

}
