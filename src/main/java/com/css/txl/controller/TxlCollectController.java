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

import com.css.base.utils.CurrentUser;
import com.css.base.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.css.txl.entity.TxlCollect;
import com.css.txl.service.TxlCollectService;


/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-20 20:26:21
 */
@Controller
@RequestMapping("/txlcollect")
public class TxlCollectController {
	@Autowired
	private TxlCollectService txlCollectService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<TxlCollect> txlCollectList = txlCollectService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(txlCollectList);
		Response.json("page",pageUtil);
	}
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public void info(@PathVariable("id") String id){
		TxlCollect txlCollect = txlCollectService.queryObject(id);
		Response.json("txlCollect", txlCollect);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(@RequestBody TxlCollect txlCollect){
		txlCollectService.save(txlCollect);
		
		Response.json("result","success");
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody TxlCollect txlCollect){
		txlCollectService.update(txlCollect);
		
		Response.json("result","success");
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] ids){
		txlCollectService.deleteBatch(ids);
		
		Response.json("result","success");
	}
	
	@RequestMapping(value = "/addorupd")
	@ResponseBody
	public void addorupd(HttpServletRequest request, String id) {
		String[] ids = id.split(",");
		for(int i =0 ;i<ids.length;i++) {
			TxlCollect collect= txlCollectService.getCollectUser(ids[i], CurrentUser.getUserId());
			if(null != collect) {
				//txlCollectService.delete(collect.getId());
			}else {
				TxlCollect item = new TxlCollect();
				item.setCollectUserid(ids[i]);
				item.setUserid(CurrentUser.getUserId());
				item.setId(UUIDUtils.random());
				txlCollectService.save(item);
			}
		}
		Response.json("result","success");
	}
	
	@RequestMapping(value = "/delSc")
	@ResponseBody
	public void delSc(HttpServletRequest request, String id) {
		String[] ids = id.split(",");
		for(int i =0 ;i<ids.length;i++) {
			TxlCollect collect= txlCollectService.getCollectUser(ids[i], CurrentUser.getUserId());
			if(null != collect) {
				txlCollectService.delete(collect.getId());
			}
		}
		Response.json("result","success");
	}
	
}
