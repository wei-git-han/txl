package com.css.txl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-20 21:01:42
 */
@Controller
@RequestMapping("/txlorgan")
public class TxlOrganController {
	@Autowired
	private TxlOrganService txlOrganService;
	@Autowired
	private AppConfig appConfig;
	@Autowired
	private TxlUserService txlUserService;
	
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getDeptTree(HttpServletRequest request) {
		JSONObject list=  getOrganTree("root");
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	
	@RequestMapping(value = "/syncTree")
	@ResponseBody
	public Object getDeptTreeSync(String id) {
		JSONArray ja=new JSONArray();
		if("#".equals(id)){
			TxlOrgan organ=txlOrganService.queryObject("root");
			JSONObject jo=new JSONObject();
			jo.put("id",organ.getOrganid());
			jo.put("parent","#");
			jo.put("text",organ.getOrganname());
			jo.put("children",true);
			ja.add(jo);
		}else{
			if(StringUtils.isEmpty(id)){
				id="root";
			}
			JSONObject jo=null;;
			boolean isManager= CurrentUser.getIsManager(appConfig.getAppId(),appConfig.getAppSecret());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id",id);
			if(!isManager){
				map.put("isShow","1");
			}
			List<TxlOrgan> organs= txlOrganService.getSubOrgSync(map);
			for(TxlOrgan organ:organs){
				jo=new JSONObject();
				jo.put("id",organ.getOrganid());
				jo.put("parent",organ.getFatherid());
				jo.put("text",organ.getOrganname());
				jo.put("isShow",organ.getIsShow());
				jo.put("children",!"0".equals(organ.getCode()));
				ja.add(jo);
			}
		}
		return ja;
	}
	
	/**根据传来的值显示或者隐藏组织机构节点
	 * isShow “0”：隐藏 ,“1”:显示
	 * @param id
	 * @param isShow
	 */
	@RequestMapping(value = "/showOrgan")
	@ResponseBody
	public void showOrgan(String id,String isShow) {
		TxlOrgan organ=txlOrganService.queryObject(id);
		//隐藏oran下所有节点
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("organId",id);
		map.put("isShow",isShow);
		//隐藏组织机构节点
		txlOrganService.hideOrgan(map);
		//隐藏所有用户
		txlUserService.hideAllUser(map);
		Response.json("result","success");
	}
	public JSONObject getOrganTree(String id){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		TxlOrgan organ = txlOrganService.queryObject(id);
		result.put("id", organ.getOrganid());
		result.put("text", organ.getOrganname());
		List<TxlOrgan> organs = txlOrganService.getSubOrg(id);
		for (TxlOrgan sysOrgan:organs) {
			JSONObject json = new JSONObject();
			json.put("id", sysOrgan.getOrganid());
			json.put("text", sysOrgan.getOrganname());
		    jsons.add(getOrganTree(sysOrgan.getOrganid()));
		}
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
	}
	@RequestMapping(value = "/authen")
	@ResponseBody
	public void showOrgan() {
		boolean isManager= CurrentUser.getIsManager(appConfig.getAppId(),appConfig.getAppSecret());
        Response.json("manager",isManager);
	}
}
