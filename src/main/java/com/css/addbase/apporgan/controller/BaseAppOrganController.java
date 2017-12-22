package com.css.addbase.apporgan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.GwPageUtils;
import com.css.base.utils.Response;
/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 */
@Controller
@RequestMapping("app/base/dept")
public class BaseAppOrganController {
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;

	/**
	 * 自定义部门树只获取全部的叶子节点
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getDeptTree(HttpServletRequest request) {
		String orgid = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		if (StringUtils.isNotEmpty(orgid)) {
			JSONObject list=  getDeptTree(orgid, false);
			JSONObject json = new JSONObject();
			json.put("opened", true);
			list.put("state", json);
			return list;
		} else {
			JSONObject list=  getDeptTree("root", false);
			JSONObject json = new JSONObject();
			json.put("opened", true);
			list.put("state", json);
			return list;
		}
		
	}
	
	/**
	 * 自定义部门树只获取root节点下的叶子节点
	 */
	@RequestMapping(value = "/tree_onlyroot")
	@ResponseBody
	public Object getDeptTreeOnlyRootChildren(HttpServletRequest request) {
		JSONObject list=  getDeptTree("root",true);
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	/**
	 * 
	 * @param id
	 * @param isRootOnly true 只获取root节点下的叶子节点  false 获取全部叶子节点
	 * @return
	 */
	public JSONObject getDeptTree(String id,boolean isRootOnly){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		BaseAppOrgan department = baseAppOrganService.queryObject(id);
		result.put("id", department.getId());
		result.put("text", department.getName());
		result.put("type", "0");
		List<BaseAppOrgan> depts = baseAppOrganService.findByParentId(id);
		for (BaseAppOrgan dept:depts) {
			JSONObject json = new JSONObject();
			json.put("id", dept.getId());
			json.put("text", dept.getName());
			json.put("type", "0");
			if (!isRootOnly) {
				jsons.add(getDeptTree(dept.getId(),false));
			} else {
				jsons.add(json);
			}
		}
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public void list(String id) {
		List<BaseAppOrgan> depts = baseAppOrganService.findByParentId(id);
		
		GwPageUtils pageUtil = new GwPageUtils(depts);
		Response.json(pageUtil);
	}
	

	/**
	 * 根据类型获取字典json数组
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{type}")
	public void info(@PathVariable("type") String type){
		JSONArray jsons = new JSONArray();
		JSONObject js = new JSONObject();
		List<BaseAppOrgan> depts = baseAppOrganService.findByParentId(type);
		for (BaseAppOrgan dept:depts) {
			JSONObject json = new JSONObject();
			json.put("value", dept.getId());
			json.put("text", dept.getName());
			jsons.add(json);
		}
		js.put("dept", jsons);
		 Response.json(js);
	}
	/**
	 * 保存、编辑
	 */
	@RequestMapping(value = "/deptname")
	@ResponseBody
	public void deptname(String id){
		if (StringUtils.isNotBlank(id)) {
			BaseAppOrgan dic = baseAppOrganService.queryObject(id);
			JSONObject json = new JSONObject();
			json.put("name", dic.getName());
			Response.json(json);
		} 
	}

}
