package com.css.txl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.css.base.utils.PageUtils;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.css.base.utils.Response;
import com.css.txl.entity.TxlRemark;
import com.css.txl.service.TxlRemarkService;


/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-05-13 13:42:33
 */
@Controller
@RequestMapping("/txlremark")
public class TxlRemarkController {
	@Autowired
	private TxlRemarkService txlRemarkService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("txlremark:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<TxlRemark> txlRemarkList = txlRemarkService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(txlRemarkList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("txlremark:info")
	public void info(@PathVariable("id") String id){
		TxlRemark txlRemark = txlRemarkService.queryObject(id);
		Response.json("txlRemark", txlRemark);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(String remarkContent, String remarkedPersonId, String remarkedPersonName){
		TxlRemark txlRemark = new TxlRemark();
		txlRemark.setId(UUIDUtils.random());
		txlRemark.setCreatedTime(new Date());
		txlRemarkService.save(txlRemark);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("txlremark:update")
	public void update(@RequestBody TxlRemark txlRemark){
		txlRemarkService.update(txlRemark);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("txlremark:delete")
	public void delete(@RequestBody String[] ids){
		txlRemarkService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
