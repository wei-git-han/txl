package com.css.txl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.PageUtils;
import com.github.pagehelper.PageHelper;
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
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<TxlUser> txlUserList = txlUserService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(txlUserList);
		Response.json("page",pageUtil);
	}
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{userid}")
	public void info(@PathVariable("userid") String userid){
		TxlUser txlUser = txlUserService.queryObject(userid);
		Response.json("txlUser", txlUser);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public void save(@RequestBody TxlUser txlUser){
		txlUserService.save(txlUser);
		
		Response.json("result","success");
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody TxlUser txlUser){
		txlUserService.update(txlUser);
		
		Response.json("result","success");
	}
	

	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public void updateUser(HttpServletRequest request, TxlUser txlUser) {
		txlUserService.update(txlUser);
		
		Response.json("result","success");
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] userids){
		txlUserService.deleteBatch(userids);
		
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
		json.put("txlOrgtel", txlUser);
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
				if(null != txlUser.getTelephone() && !"".equals(txlUser.getTelephone())) {
					dutyJson.put("zuoji", txlUser.getTelephone());
				}else {
					dutyJson.put("zuoji", "");
				}
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
}
