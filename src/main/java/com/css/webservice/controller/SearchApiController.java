package com.css.webservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.JSONUtil;
import com.css.base.utils.Response;
import com.css.txl.service.TxlUserService;

@Controller
@RequestMapping("/api/search")
public class SearchApiController {
	@Autowired
	private TxlUserService txlUserService;
	/**
	 * 1、搜索范围：表[TXL_USER],字段：FULLNAME、MOBILE
	 * @param request
	 * @param json
	 */
	@ResponseBody
	@RequestMapping("/data")
	public void data(HttpServletRequest request, String json) {
		JSONObject obj = JSONUtil.jsonFailed(json);
		if(obj==null){return;}
		String keywords=obj.getString("keywords");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keywords", keywords);
		int total=txlUserService.queryTotal(map);
		JSONObject result=JSONUtil.resultSuccess(total, "");
		Response.json(result);
	}
}
