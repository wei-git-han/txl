 package com.css.txl.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.css.adminconfig.entity.AdminSet;
import com.css.adminconfig.service.AdminSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;


import dm.jdbc.util.StringUtil;

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

	@Autowired
	private AdminSetService adminSetService;

	@Value("${csse.mircoservice.zuul}")
	private String zuul;
	private final Logger logger = LoggerFactory.getLogger(TxlUserController.class);


	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public void updateUser(HttpServletRequest request, TxlUser txlUser) {
		if(StringUtil.isNotEmpty(txlUser.getMobileTwo())&&StringUtil.isEmpty(txlUser.getMobile())) {
			String[] mobiles=txlUser.getMobileTwo().split(",");
			String mobileTwo="";
			for (int i=0;i< mobiles.length;i++) {
				if(i==0) {
					txlUser.setMobile(mobiles[0]);
				}else {
					mobileTwo+=(mobiles[i]+",");
				}
			}
			if(StringUtil.isNotEmpty(mobileTwo)) {
				mobileTwo=mobileTwo.substring(0, mobileTwo.length()-1);
			}
			
			txlUser.setMobileTwo(mobileTwo);
		}
		if(StringUtil.isNotEmpty(txlUser.getTelephoneTwo())&&StringUtil.isEmpty(txlUser.getTelephone())) {
			String[] telephones=txlUser.getTelephoneTwo().split(",");
			String telephoneTwo="";
			for (int i=0;i< telephones.length;i++) {
				if(i==0) {
					txlUser.setTelephone(telephones[0]);
				}else {
					telephoneTwo+=(telephones[i]+",");
				}
			}
			if(StringUtil.isNotEmpty(telephoneTwo)) {
				telephoneTwo=telephoneTwo.substring(0, telephoneTwo.length()-1);
			}
			
			txlUser.setTelephoneTwo(telephoneTwo);
		}
		txlUserService.update(txlUser);
		txlUser = txlUserService.queryObject(txlUser.getUserid());
		String url = zuul + "/api/org/userinfo/" + txlUser.getUserid();
		JSONObject user = new JSONObject();
		user.put("fullname", txlUser.getFullname());
		user.put("isManager", "0");
		user.put("organId", txlUser.getOrganid());
		JSONArray OrganIds=new JSONArray();
		OrganIds.add(txlUser.getOrganid());
		user.put("organIds",OrganIds);
		
		user.put("secLevel", txlUser.getSeclevel());
		user.put("sex", txlUser.getSex());
		if(!StringUtils.isEmpty(txlUser.getMobile())){
			user.put("mobile", txlUser.getMobile());
		}
		if(!StringUtils.isEmpty(txlUser.getTelephone())){
			user.put("tel", txlUser.getTelephone());
		}
		
		
		JSONObject result = getJsonData(url,user);
		System.out.println(result);
		Date date = new Date();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
		logger.info("当前修改用户个人信息操作人："+CurrentUser.getSSOUser().getFullname()+"---id:"+CurrentUser.getUserId()+"--时间是："+format);
//		CrossDomainUtil.getJsonData(url, map);
		Response.json("result","success");
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

	@RequestMapping(value = "/bareauTree")
	@ResponseBody
	public Object getBureauTree(HttpServletRequest request) {
		JSONObject list=  getUserTree(txlOrganService.getBarOrgIdByUserId(CurrentUser.getUserId()));
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	
	@RequestMapping(value = "/getCurrentUserId")
	@ResponseBody
	public void getCurrentUserId() {
		String CurrentUserId = CurrentUser.getUserId();
		Response.json("CurrentUserId",CurrentUserId);
	}
	
	@RequestMapping(value = "/getUserPostForQxj")
	@ResponseBody
	public void getUserPostForQxj(HttpServletRequest request, String id) {
		JSONObject json = new JSONObject();
		TxlUser txlUser = txlUserService.queryObject(id);
		json.put("post", txlUser.getPost()==null ? "" :txlUser.getPost());
		json.put("address", txlUser.getAddress()==null ? "" :txlUser.getAddress());
		json.put("phone", txlUser.getMobile()==null ? "" :txlUser.getMobile());
		json.put("id", txlUser.getUserid());
		json.put("text", txlUser.getFullname());
		Response.json(json);
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
//		System.out.println(CurrentUser.getIsManager(appConfig.getAppId(),appConfig.getAppSecret()));
		if(StringUtils.isNotBlank(txlUser.getRights()) /*&& !CurrentUser.getIsManager(appConfig.getAppId(),appConfig.getAppSecret())*/){
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
		//查询当前登录人是否为局管理员或部管理员
		String typeByUserId = adminSetService.getAdminTypeByUserId(userId);
		//如果登录人具有管理员权限
		if(StringUtils.isNotEmpty(typeByUserId)){
			//查询登录人所属部门
			Map<String, Object> adminMap = new HashMap<>();
			adminMap.put("userId",userId);
			List<AdminSet> adminList = adminSetService.queryList(adminMap);
			if(null!=adminList&&adminList.size()>0){
				AdminSet adminSet = adminList.get(0);
				String orgId = adminSet.getOrgId();
				json.put("orgId",orgId);
			}
			//判断具有部管理员还是局管理员
			if("3".equals(typeByUserId)||"1".equals(typeByUserId)){
			json.put("isBuManager",true);
			json.put("isJuManager",true);
			}else if("2".equals(typeByUserId)){
			json.put("isJuManager",true);
			json.put("isBuManager",false);
			}else {
				json.put("isBuManager",false);
				json.put("isJuManager",false);
			}

		}else {
			json.put("orgId","");
		}
		String orgIdToSelf = txlOrganService.getBarOrgIdByUserId(txlUser.getUserid());
		json.put("orgIdToSelf",orgIdToSelf);
		json.put("txlOrgtel", txlUser);
		json.put("ismyself", txlUser.getUserid().equals(userId));
		Response.json(json);
	}
	
	@RequestMapping(value = "/getUserTreeForqxj")
	@ResponseBody
	public Object getUserTreeForqxj(HttpServletRequest request) {
		JSONObject list=  getUserTreeQxj("root");
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	
	public JSONObject getUserTreeQxj(String id){
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
		    jsons.add(getUserTreeQxj(sysOrgan.getOrganid()));
		}
		List<TxlUser> sysUsers = txlUserService.getUserInfos(id);
		for (TxlUser sysUser:sysUsers) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("id", sysUser.getUserid());
			jsonUser.put("text", sysUser.getFullname());
			jsonUser.put("type", "1");
			jsonUser.put("post", sysUser.getPost()==null ? "" :sysUser.getPost());
			jsons.add(jsonUser);
		}
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
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
        	//发送put请求
        	ResponseEntity<JSONObject> data = restTemplate.exchange(url, HttpMethod.PUT, entity, JSONObject.class);
        	return data.getBody();
        }catch(Exception e){
        	System.out.println("【报错信息】"+e.getMessage()+"，url="+url);
        }
        return null;
	}
}
