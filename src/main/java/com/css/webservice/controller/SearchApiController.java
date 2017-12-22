package com.css.webservice.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.JSONUtil;
import com.css.addbase.PinYinUtil;
import com.css.addbase.orgservice.OrgService;
import com.css.base.entity.SSOUser;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.txl.service.TxlUserService;
import com.css.webservice.entity.SearchEntity;

@Controller
@RequestMapping("/api/search")
public class SearchApiController {
	@Autowired
	private TxlUserService txlUserService;
	@Autowired
	private OrgService orgService;
	@Value("${csse.appID}")
	private String appId;
	/**
	 * 1、搜索范围：表[TXL_USER],字段：FULLNAME、MOBILE
	 * @param request
	 * @param json
	 */
	@ResponseBody
	@RequestMapping("/data")
	public void data(@RequestBody SearchEntity entity) {
		JSONObject obj=jsonFailed(entity);
		if(obj==null){return;}
		String userId=obj.getString("userId");
		String[] keyWords=obj.getObject("keyWords", String[].class);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keywords", keyWords);
		if (PinYinUtil.hasZm(keyWords)) {
			map.put("zm", "1");
		}
		int total=txlUserService.queryTotal(map);
		String link="/index.html";
		JSONObject result=JSONUtil.resultSuccess(total,appId, link);
		Response.json(result);
	}
	private JSONObject jsonFailed(SearchEntity entity) {
		String token = entity.getToken();
		String[] keyWords=entity.getKeyWords();
		
		JSONObject jsonFailed = new JSONObject();
		jsonFailed.put("result", "failed");
		if (keyWords == null || keyWords.length <= 0) {
			jsonFailed.put("msg", "keyWords参数为空或不存在！");
			Response.json(jsonFailed);
			return null;
		}
		SSOUser user = orgService.getSUser(token);
		String userId = user != null ? user.getUserId() : "";
		String userName=user!=null?user.getFullname():"";
		if (user == null||StringUtils.isBlank(userId)) {
			jsonFailed.put("msg", "token参数为空或不正确！");
			Response.json(jsonFailed);
			return null;
		}else{
			JSONObject json=new JSONObject();
			json.put("userId", userId);
			json.put("userName", userName);
			json.put("keyWords", keyWords);
			return json;
		}
	}
}
