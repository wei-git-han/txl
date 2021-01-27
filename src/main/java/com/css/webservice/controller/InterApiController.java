package com.css.webservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.base.utils.Response;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlUserService;

import dm.jdbc.util.StringUtil;

@Controller
@RequestMapping("/api")
public class InterApiController {
	@Autowired
	private TxlUserService txlUserService;
	
	@ResponseBody
	@RequestMapping("/userSearch")
	public void  fypUserSearch(String userIds) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(userIds!=null) {
			map.put("userIds", userIds.split(","));
		}
		JSONArray ja=new JSONArray();
	    JSONObject jo=new JSONObject();
		List<TxlUser> users=txlUserService.queryList(map);
		for(TxlUser txlUser:users) {
			jo = new JSONObject();
			jo.put("id", StringUtil.isNotEmpty(txlUser.getUserid())?txlUser.getUserid():"");
			jo.put("userName", StringUtil.isNotEmpty(txlUser.getFullname())?txlUser.getFullname():"");
			jo.put("tel", StringUtil.isNotEmpty(txlUser.getTelephone())?txlUser.getTelephone():"");
			jo.put("mobile", StringUtil.isNotEmpty(txlUser.getMobile())?txlUser.getMobile():"");
			jo.put("post", StringUtil.isNotEmpty(txlUser.getPost())?txlUser.getPost():"");
			jo.put("address", StringUtil.isNotEmpty(txlUser.getAddress())?txlUser.getAddress():"");
			ja.add(jo);
		}
		Response.json("rows",ja);
	}

}
