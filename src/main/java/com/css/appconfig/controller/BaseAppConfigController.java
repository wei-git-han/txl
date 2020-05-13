package com.css.appconfig.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.css.appconfig.entity.BaseAppConfig;
import com.css.appconfig.service.BaseAppConfigService;
import com.css.base.utils.Response;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 键值配置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 */
@Controller
@RequestMapping("app/base/conf")
public class BaseAppConfigController {
	@Autowired
	private BaseAppConfigService baseAppConfigService;
	/**
	 * 根据名称获取值
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/info")
	public void info(String name){
		if(!StringUtils.isEmpty(name)) {
			BaseAppConfig conf =baseAppConfigService.queryObject(name);
			JSONObject jo=JSONObject.fromObject(conf);
			Response.json(jo);
		}
		
	}

	/**
	 * 2018年5月31日9:46:34
	 * 根据名称获取值 紧急程度  密级字典数据
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDictBykey")
	public void getDictBykey(String key){
		com.alibaba.fastjson.JSONObject jo =new com.alibaba.fastjson.JSONObject();
		JSONArray ja =new JSONArray();
		if(key.equals("security_classification")||key.equals("emergency_gegree")) {
			if(!StringUtils.isEmpty(key)) {
				BaseAppConfig conf = baseAppConfigService.queryObject(key);	
				String value = conf.getValue();
				ja=JSON.parseArray(value);
			}
		}
		jo.put(key, ja);
		Response.json(jo);
	}
	
	/**
	 * 更新办件标记和办件起始值
	 * @param banjianCode
	 * @param serialNum
	 */	
	@ResponseBody
	@RequestMapping("/updateConfig")
	public void saveSerialNumConfig(String banjianCode,String serialNum,String shuiyinwenzi){
		String result ="";
		if(StringUtils.isNotBlank(serialNum)){
			int ret = baseAppConfigService.UpdateValueByType(serialNum, "banjian_start_serialNum");
			if(ret>0){
				result="success";
			}			
		}
		if(StringUtils.isNotBlank(banjianCode)){
			int ret = baseAppConfigService.UpdateValueByType(banjianCode, "banjian_code");
			if(ret>0){
				result="success";
			}
		}
		if(StringUtils.isNotBlank(shuiyinwenzi)){
			int ret = baseAppConfigService.UpdateValueByType(shuiyinwenzi, "shuiyinwenzi");
			if(ret>0){
				result="success";
			}
		}
		Response.json("result",result);
	}
	/**
	 * 获得办件标记和办件起始值
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("/getConfig")
	public void getSerialNumConfig(String key){	
		JSONObject jsons =new JSONObject();
		if(StringUtils.isNotBlank(key)){
			String[] keys=key.split(",");
			for (String str : keys) {
				BaseAppConfig conf = baseAppConfigService.queryObject(str);		
				if(StringUtils.equals("banjian_code", str)){
					jsons.put("code", conf.getValue());
				}else if(StringUtils.equals("banjian_start_serialNum", str)){
					jsons.put("num", conf.getValue());
				}else if(StringUtils.equals("shuiyinwenzi", str)){
					jsons.put("word", conf.getValue());
				}
			}			
		}
		Response.json(jsons);
	}
}
