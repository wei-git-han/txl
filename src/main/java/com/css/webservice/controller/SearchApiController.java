package com.css.webservice.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.addbase.JSONUtil;
import com.css.addbase.PinYinUtil;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.txl.entity.TxlCollect;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlCollectService;
import com.css.txl.service.TxlUserService;

import dm.jdbc.util.StringUtil;

@Controller
@RequestMapping("/api/search")
public class SearchApiController {
	@Autowired
	private TxlUserService txlUserService;
	@Autowired
	private TxlCollectService txlCollectService;
	@Autowired
	private AppConfig appConfig;

	@Value("${csse.txl.appId}")
	private String appId;
	/**
	 * 1、搜索范围：表[TXL_USER],字段：FULLNAME、MOBILE
	 * @param request
	 * @param json
	 */
	@ResponseBody
	@RequestMapping("/data")
	public void data(HttpServletRequest request) {
		sysPrint(request);
		JSONObject obj=jsonFailed(request);
		if(obj==null){return;}
		String[] keyWords=obj.getObject("keyWords", String[].class);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keywords", keyWords);
		if (PinYinUtil.hasZm(keyWords)) {
			map.put("zm", "1");
		}
		int total=txlUserService.queryTotal(map);
		String link="/index.html?";
		JSONObject result=JSONUtil.resultSuccess(total,appId, link);
		Response.json(result);
	}
	private JSONObject jsonFailed(HttpServletRequest request) {
		String kds=request.getParameter("keyWords");
		String[] keyWords=toArray(kds);
		
		JSONObject jsonFailed = new JSONObject();
		jsonFailed.put("result", "failed");
		if (keyWords == null || keyWords.length <= 0) {
			jsonFailed.put("msg", "keyWords参数为空或不存在！");
			Response.json(jsonFailed);
			return null;
		}
		JSONObject json=new JSONObject();
		json.put("keyWords", keyWords);
		return json;
	}
	/**
	 * kds的格式：[u'123',u'ces']
	 * @param kds
	 * @return
	 */
	private static String[] toArray(String kds) {
		kds=unicode2String(kds);
		System.out.println("【keyWords】"+kds);
		String pattern="([^ \"]+)";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(kds);
		List<String> keyWords=new ArrayList<String>();
		while(m.find()){
			keyWords.add(m.group(1));
		}
		return keyWords.toArray(new String[]{});
	}
	/**
	 * 1、把unicode的编码字符串转换为该编码对应的字符串
	 * @param unicodeStr
	 * @return
	 */
	public static String unicode2String(String unicodeStr){
		Pattern p=Pattern.compile("(\\\\(\\p{XDigit}{4}))");
		char ch;
		Matcher m=p.matcher(unicodeStr);
		StringBuffer sb=new StringBuffer();
		while(m.find()){
			if(m.groupCount()>1){
				ch=(char)Integer.parseInt(m.group(2),16);
			}else{
				ch=(char)Integer.parseInt(m.group(1),10);
			}
			m.appendReplacement(sb, ch+"");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	@ResponseBody
	@RequestMapping("/fypSearch")
	public void  searchFyp(String value,String callback) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("search", value);
		if (PinYinUtil.hasZm(value)) {
			map.put("zm",value);
		}
		JSONArray ja=new JSONArray();
	    JSONObject jo=new JSONObject();
		List<TxlUser> users=txlUserService.queryList(map);
		for(TxlUser txlUser:users) {
			jo=new JSONObject();
			jo.put("name", StringUtil.isNotEmpty(txlUser.getFullname())?txlUser.getFullname():"");
			jo.put("phone", StringUtil.isNotEmpty(txlUser.getTelephone())?txlUser.getTelephone():"");
			jo.put("tel", StringUtil.isNotEmpty(txlUser.getMobile())?txlUser.getMobile():"");
			if("xlglGetTxlInfo".equals(callback)) { // 训练管理获取通讯录额外需要的字段
				jo.put("userId", StringUtil.isNotEmpty(txlUser.getAccount())?txlUser.getAccount():"");
				jo.put("post", StringUtil.isNotEmpty(txlUser.getPost())?txlUser.getPost():"");
				jo.put("address", StringUtil.isNotEmpty(txlUser.getAddress())?txlUser.getAddress():"");
				jo.put("dept", StringUtil.isNotEmpty(txlUser.getDept())?txlUser.getDept():"");
			}
			ja.add(jo);
		}
		//Response.json(ja);
		String message = JSON.toJSONString(ja);
		if("xlglGetTxlInfo".equals(callback)) {
			Response.json(ja);
		} else {
			Response.stringJsonp(message, callback);
		}
	}
	@ResponseBody
	@RequestMapping("/fypUserSearch")
	public void  fypUserSearch(String userIds) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userIds", userIds.split(","));
		JSONArray ja=new JSONArray();
	    //JSONObject jo=new JSONObject();
		List<TxlUser> users=txlUserService.queryList(map);
		for(TxlUser txlUser:users) {
			JSONObject jo=new JSONObject();
			jo.put("userName", StringUtil.isNotEmpty(txlUser.getFullname())?txlUser.getFullname():"");
			jo.put("phone", StringUtil.isNotEmpty(txlUser.getTelephone())?txlUser.getTelephone():"");
			jo.put("tel", StringUtil.isNotEmpty(txlUser.getMobile())?txlUser.getMobile():"");
			jo.put("userId", StringUtil.isNotEmpty(txlUser.getAccount())?txlUser.getAccount():"");
			jo.put("post", StringUtil.isNotEmpty(txlUser.getPost())?txlUser.getPost():"");
			jo.put("address", StringUtil.isNotEmpty(txlUser.getAddress())?txlUser.getAddress():"");
			jo.put("dept", StringUtil.isNotEmpty(txlUser.getDept())?txlUser.getDept():"");
			ja.add(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list",ja);
		Response.json(jsonObject);
	}
	/**
	 * 负一屏默认搜索sc的前6个;搜索时搜索全部
	 * @param request
	 * @author xiayj
	 */
	@ResponseBody
	@RequestMapping("/fypCollect")
	public void  getFypSc(String callback,HttpServletRequest request) {
		JSONObject jo=new JSONObject();
		jo.put("maxtitle", "通讯录");
//		String href=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		String href=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		jo.put("href", "/index.html");
		jo.put("searchurl","/api/search/fypSearch");
		jo.put("appid", appConfig.getAppId());
		JSONArray jsons = new JSONArray();
		jsons = getScJson();
		jo.put("data",jsons);
		String message = JSON.toJSONString(jo);
		Response.stringJsonp(message, callback);
		
	}
	
	@RequestMapping(value = "/ljFypTxlUser")
	@ResponseBody
	public void listuser(String searchValue,String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		String currentUserId = CurrentUser.getUserId();
		List<TxlUser> liInfos=null;
		if (StringUtils.isNotBlank(searchValue)) {
			searchValue = searchValue.replace(" ", "");
			map.put("search", searchValue);
			if (PinYinUtil.hasZm(searchValue)) {
				map.put("zm", searchValue);
			}
			boolean isManager = CurrentUser.getIsManager(appConfig.getAppId(), appConfig.getAppSecret());
			if (!isManager) {
				map.put("isShow", "1");// 1代表显示的，0和空为隐藏
			}
			map.put("currentUserId", currentUserId);
			liInfos = txlUserService.queryList(map);
		}else {
			liInfos = txlUserService.getTxlFavorite(currentUserId);
		}		
		JSONObject ja=new JSONObject();
		ja.put("txlList", liInfos);
		ja.put("appId", appId);
		ja.put("href", "/index.html");
		String message = JSON.toJSONString(ja);
		Response.stringJsonp(message, callback);
	}
	private JSONArray getScJson() {
		// 获取收藏
		List<TxlCollect> collects = txlCollectService.getCollect(CurrentUser.getUserId());
		JSONArray jsons = new JSONArray();
		JSONObject json =null;
		int i=0;
		for (TxlCollect collect : collects) {
			if(i>=6) {
				break;
			}
		    json = new JSONObject();
			TxlUser txlUser = txlUserService.queryObject(collect.getCollectUserid());
			if (txlUser == null) {
				continue;
			}
			txlUser.setIsSc("true");
			if ((CurrentUser.getIsManager(appConfig.getAppId(), appConfig.getAppSecret()))||(!"0".equals(txlUser.getIsShow()))) {
				json.put("name", StringUtil.isNotEmpty(txlUser.getFullname())?txlUser.getFullname():"");
				json.put("phone", StringUtil.isNotEmpty(txlUser.getTelephone())?txlUser.getTelephone():"");
				json.put("tel", StringUtil.isNotEmpty(txlUser.getMobile())?txlUser.getMobile():"");
				jsons.add(json);
				i++;
			}
		}
		return jsons;
	}

	private void sysPrint(HttpServletRequest request){
		Enumeration<String> enums=request.getParameterNames();
		while(enums.hasMoreElements()){
			String key=enums.nextElement();
			System.out.println("【搜索接口参数】"+key+"->"+toStr(request.getParameterValues(key)));
		}
	}
	
	private String toStr(String[] values) {
		StringBuilder sb=new StringBuilder();
		if(values!=null&&values.length>0){
			for(String value:values){
				sb.append(","+value);
			}
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}
}
