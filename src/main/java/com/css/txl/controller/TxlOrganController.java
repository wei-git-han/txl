package com.css.txl.controller;

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
import com.css.base.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.css.base.utils.Response;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.service.TxlOrganService;

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
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<TxlOrgan> txlOrganList = txlOrganService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(txlOrganList);
		Response.json("page",pageUtil);
	}
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{organid}")
	public void info(@PathVariable("organid") String organid){
		TxlOrgan txlOrgan = txlOrganService.queryObject(organid);
		Response.json("txlOrgan", txlOrgan);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(@RequestBody TxlOrgan txlOrgan){
		txlOrganService.save(txlOrgan);
		
		Response.json("result","success");
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody TxlOrgan txlOrgan){
		txlOrganService.update(txlOrgan);
		
		Response.json("result","success");
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] organids){
		txlOrganService.deleteBatch(organids);
		
		Response.json("result","success");
	}
	
	
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getDeptTree(HttpServletRequest request) {
		JSONObject list=  getOrganTree("root");
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
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
	
}
