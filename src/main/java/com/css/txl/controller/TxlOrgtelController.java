package com.css.txl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.PageUtils;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.css.base.utils.Response;
import com.css.txl.entity.TxlOrgtel;
import com.css.txl.service.TxlOrgtelService;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-25 15:08:05
 */
@Controller
@RequestMapping("/txlorgtel")
public class TxlOrgtelController {
	@Autowired
	private TxlOrgtelService txlOrgtelService;
	@Autowired
	private AppConfig appConfig;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("txlorgtel:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<TxlOrgtel> txlOrgtelList = txlOrgtelService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(txlOrgtelList);
		Response.json("page",pageUtil);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/listBmdh")
	@ResponseBody
	public void listBmdh(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		//查询列表数据
		List<TxlOrgtel> txlOrgtelList = txlOrgtelService.queryList(map);
		Response.json(txlOrgtelList);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping(value = "/info")
	@ResponseBody
	public void info(String id){
		JSONObject json = new JSONObject();
		TxlOrgtel txlOrgtel = txlOrgtelService.queryObject(id);
		json.put("manager", CurrentUser.getIsManager(appConfig.getAppId()));
		json.put("txlOrgtel", txlOrgtel);
		Response.json(json);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public void save(TxlOrgtel txlOrgtel){
		txlOrgtel.setId(UUIDUtils.random());
		txlOrgtelService.save(txlOrgtel);
		
		Response.json("result","success");
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public void update(TxlOrgtel txlOrgtel){
		txlOrgtelService.update(txlOrgtel);
		
		Response.json("result","success");
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public void delete(String[] ids){
		txlOrgtelService.deleteBatch(ids);
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteone")
	@ResponseBody
	public void deleteone(String id){
		txlOrgtelService.delete(id);
		Response.json("result","success");
	}
	
}
