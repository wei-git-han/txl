package com.css.txl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.base.utils.CurrentUser;
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
