package com.css.addbase;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * JSON对象转化类
 */
public class JSONUtil {

	public static Object toJSON(Object javaObject) {
		return toJSON(javaObject, SerializeConfig.globalInstance);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static Object toJSON(Object javaObject, SerializeConfig config) {
		if (javaObject == null) {
			return "";
		}

		if (javaObject instanceof JSON) {
			return javaObject;
		}

		if (javaObject instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) javaObject;

			JSONObject json = new JSONObject(map.size());

			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				Object key = entry.getKey();
				String jsonKey = TypeUtils.castToString(key);
				Object jsonValue = toJSON(entry.getValue());
				json.put(jsonKey, jsonValue);
			}

			return json;
		}

		if (javaObject instanceof Collection) {
			Collection<Object> collection = (Collection<Object>) javaObject;

			JSONArray array = new JSONArray(collection.size());

			for (Object item : collection) {
				Object jsonValue = toJSON(item);
				array.add(jsonValue);
			}

			return array;
		}

		Class<?> clazz = javaObject.getClass();

		if (clazz.isEnum()) {
			return ((Enum<?>) javaObject).name();
		}

		if (clazz.isArray()) {
			int len = Array.getLength(javaObject);

			JSONArray array = new JSONArray(len);

			for (int i = 0; i < len; ++i) {
				Object item = Array.get(javaObject, i);
				Object jsonValue = toJSON(item);
				array.add(jsonValue);
			}

			return array;
		}

		if (ParserConfig.isPrimitive2(clazz)) {
			return javaObject;
		}

		ObjectSerializer serializer = config.getObjectWriter(clazz);
		if (serializer instanceof JavaBeanSerializer) {
			JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) serializer;

			JSONObject json = new JSONObject();
			try {
				Map<String, Object> values = javaBeanSerializer.getFieldValuesMap(javaObject);
				for (Map.Entry<String, Object> entry : values.entrySet()) {
					json.put(entry.getKey(), toJSON(entry.getValue()));
				}
			} catch (Exception e) {
				throw new JSONException("toJSON error", e);
			}
			return json;
		}

		String text = JSON.toJSONString(javaObject);
		return JSON.parse(text);
	}

	/**
	 * 1、搜索成功后，显示结果数据。<br/>
	 * 2、本方法用于搜索接口。
	 * @param total
	 * @param link
	 * @return
	 */
	public static JSONObject resultSuccess(int total,String appId, String link) {
		JSONObject result = new JSONObject();
		result.put("result", "success");
		result.put("total", total);
		result.put("appId", appId);
		result.put("link", link);
		return result;
	}
}
