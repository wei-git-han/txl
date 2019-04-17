package com.css.txl.controller;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.base.filter.SSOAuthFilter;
import com.css.base.utils.CrossDomainUtil;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-21 09:21:03
 */
@Controller
@RequestMapping("/txluser")
public class TxlUserController {
	@Autowired
	private TxlUserService txlUserService;
	
	@Autowired
	private TxlOrganService txlOrganService;
	
	@Autowired
	private AppConfig appConfig;

	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public void updateUser(HttpServletRequest request, TxlUser txlUser) {
		txlUserService.update(txlUser);
		String url = "http://172.16.4.3:10005/api/org/userinfo/" + txlUser.getUserid();
		JSONObject user = new JSONObject();
		if(!StringUtils.isEmpty(txlUser.getMobile())){
			user.put("mobile", txlUser.getMobile());
		}
		if(!StringUtils.isEmpty(txlUser.getTelephone())){
			user.put("tel", txlUser.getTelephone());
		}
		
		JSONObject result = getJsonData(url,user);
//		CrossDomainUtil.getJsonData(url, map);
		Response.json("result",result);
	}
	
	
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getUserTree(HttpServletRequest request) {
		JSONObject list=  getUserTree("root");
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public void getUser(HttpServletRequest request, String id) {
		JSONObject json = new JSONObject();
		TxlUser txlUser = txlUserService.queryObject(id);
		TxlOrgan txlOrgan = txlOrganService.queryObject(txlUser.getOrganid());
		if (txlOrgan != null) {
			txlUser.setOrganName(txlOrgan.getOrganname());
		}
		json.put("manager", CurrentUser.getIsManager(appConfig.getAppId(),appConfig.getAppSecret()));
		if(StringUtils.isNotBlank(txlUser.getRights())){
			String rights=txlUser.getRights();
			if(rights.indexOf("1")==-1){
				txlUser.setMobile("");
			}
			if(rights.indexOf("2")==-1){
				txlUser.setTelephone("");
			}
			if(rights.indexOf("3")==-1){
				txlUser.setPost("");
			}
			if(rights.indexOf("4")==-1){
				txlUser.setAddress("");
			}
		}
		String userId=CurrentUser.getUserId();
		json.put("txlOrgtel", txlUser);
		json.put("ismyself", txlUser.getUserid().equals(userId));
		Response.json(json);
	}
	
	
	public JSONObject getUserTree(String id){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		TxlOrgan organ = txlOrganService.queryObject(id);
		result.put("id", organ.getOrganid());
		result.put("text", organ.getOrganname());
		result.put("type", "0");
		List<TxlOrgan> organs = txlOrganService.getSubOrg(id);
		for (TxlOrgan sysOrgan:organs) {
			JSONObject json = new JSONObject();
			json.put("id", sysOrgan.getOrganid());
			json.put("text", sysOrgan.getOrganname());
			json.put("type", "0");
		    jsons.add(getUserTree(sysOrgan.getOrganid()));
		}
		List<TxlUser> sysUsers = txlUserService.getUserInfos(id);
		for (TxlUser sysUser:sysUsers) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("id", sysUser.getUserid());
			jsonUser.put("text", sysUser.getFullname());
			jsonUser.put("type", "1");
			jsons.add(jsonUser);
		}
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
	}
	
	
	@RequestMapping(value = "/getTxlInfo")
	@ResponseBody
	public void getTxlInfo(HttpServletRequest request, String fullName) {
		List<TxlUser> liInfos = new ArrayList<TxlUser>();
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		if(null != fullName && !"".equals(fullName)) {
			liInfos =  txlUserService.getNameToUser(fullName);
			for(TxlUser txlUser:liInfos) {
				JSONObject dutyJson = new JSONObject();
				dutyJson.put("xingming", txlUser.getFullname());
				dutyJson.put("shouji", txlUser.getMobile());
				dutyJson.put("zuoji", txlUser.getTelephone() == null ? "" : txlUser.getTelephone());
				dutyJson.put("address", txlUser.getAddress() == null ? "" : txlUser.getAddress());
				jsons.add(dutyJson);
			}
		}
		result.put("info",jsons);
		Response.json(result);
	}
	/**
	 * 获取当前登录人的收藏信息
	 */
	@RequestMapping(value = "/getTxlFavorite")
	@ResponseBody
	public void getTxlFavorite() {
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		String userId=CurrentUser.getUserId();
		List<TxlUser> liInfos =  txlUserService.getTxlFavorite(userId);
			for(TxlUser txlUser:liInfos) {
				JSONObject dutyJson = new JSONObject();
				dutyJson.put("name", txlUser.getFullname());
				dutyJson.put("mobile", txlUser.getMobile());
				dutyJson.put("orgName", StringUtils.isNotBlank(txlUser.getOrgName())?txlUser.getOrgName():"");
				if(null != txlUser.getTelephone() && !"".equals(txlUser.getTelephone())) {
					dutyJson.put("tel", txlUser.getTelephone());
				}else {
					dutyJson.put("tel", "");
				}
				jsons.add(dutyJson);
			}
		result.put("rows",jsons);
		result.put("total", liInfos.size());
		result.put("page", 1);
		Response.json(result);
	}
	
	public JSONObject getJsonData(String url,JSONObject jsonObject){
		//设置消息头
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
		headers.setContentType(mediaType);
		//设置请求参数
        //创建RestTemplate对象
        RestTemplate restTemplate = new RestTemplate();
        url+="?access_token=" + appConfig.getAccessToken();
        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(jsonObject, headers);
        try{
        	//发送post请求
        	ResponseEntity<JSONObject> data = restTemplate.exchange(url, HttpMethod.PUT, entity, JSONObject.class);
        	return data.getBody();
        }catch(Exception e){
        	System.out.println("【报错信息】"+e.getMessage()+"，url="+url);
        }
        return null;
	}
}
