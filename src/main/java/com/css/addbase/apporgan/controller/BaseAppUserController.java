package com.css.addbase.apporgan.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.orgservice.OrgService;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-15 10:37:13
 */
@Controller
@RequestMapping("app/base/user")
public class BaseAppUserController {
	
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	
	@Autowired
	private BaseAppUserService baseAppUserService;
	
	@Autowired
	private OrgService orgService;
	
	/**
	 * 人员列表
	 * @param organid 组织机构Id
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public void getList(String organid) {
		if (StringUtils.isEmpty(organid)) {
			organid = "root";
		}
		List<BaseAppUser> users = baseAppUserService.findByOrganid(organid);
		JSONObject result = new JSONObject();
		result.put("page", 1);
		result.put("total", users.size());
		JSONArray jsons = new JSONArray();
		for (BaseAppUser user:users) {
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("truename", user.getTruename());
			json.put("work", "");
			json.put("departmentName", baseAppOrganService.queryObject(user.getOrganid()).getName());
			jsons.add(json);
		}
		result.put("rows", jsons);
		Response.json(result);
	}
	
	/**
	 * 加载人员树
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getUserTree(HttpServletRequest request) {
		JSONObject list=  getUserTree("root");
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	
	public JSONObject getUserTree(String id){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		BaseAppOrgan dept = baseAppOrganService.queryObject(id);
		result.put("id", dept.getId());
		result.put("text", dept.getName());
		result.put("type", "0");
		
		List<BaseAppUser> sysUsers = baseAppUserService.findByOrganid(id);
		for (BaseAppUser sysUser:sysUsers) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("id", sysUser.getUserId());
			jsonUser.put("text", sysUser.getTruename());
			jsonUser.put("type", "1");
			jsonUser.put("deptid", sysUser.getOrganid());
			jsonUser.put("tel", sysUser.getMobile());
			jsons.add(jsonUser);
		}
		
		List<BaseAppOrgan> organs = baseAppOrganService.findByParentId(id);
		for (BaseAppOrgan organ:organs) {
			JSONObject json = new JSONObject();
			json.put("id", organ.getId());
			json.put("text", organ.getName());
			json.put("type", "0");
		    jsons.add(getUserTree(organ.getId()));
		}
		
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
	}
	
	/**
	 * 导入系统部门及其所属子部门和人员
	 */
	@ResponseBody
	@RequestMapping("/importOrg")
	public void importOrg(String deptIds) {
		Set<String> userSet = new HashSet<String>();
		Set<String> deptSet = new HashSet<String>();
		if (StringUtils.isNotEmpty(deptIds)) {
			String[] depts = deptIds.split(",");
			for (String dept:depts) {
				userSet.add(dept);
				Organ organ = orgService.getOrgan(dept);
				String[] paths = organ.getP().split(",");
				for (String path:paths) {
					deptSet.add(path);
				}
			}
			deptSet.remove("-1");
			importUsers(deptSet,userSet);
			Response.json("result", "success");
		}
		
	}
	
	/**
	 * 递归导入指定部门及其所属子部门和人员
	 * @param String deptId
	 */
	private void importUsers(Set<String> deptSet,Set<String> userSet) {
		//baseAppOrganService.clearOrgan();
		//baseAppUserService.clearUser();
		for (String deptId:deptSet) {
			Organ organ = orgService.getOrgan(deptId);
			BaseAppOrgan dept = baseAppOrganService.queryObject(deptId);
			if (null == dept) {
				dept = new BaseAppOrgan();
				dept.setId(organ.getOrganId());
				dept.setName(organ.getOrganName());
				dept.setParentId(organ.getFatherId());
				dept.setTreePath(organ.getP());
				dept.setSort(organ.getOrderId());
				dept.setDeptOfficer(-1);//默认值为-1， 0-处室， 1-局 FIXME 如何确定部门级别？
				dept.setIsdelete(organ.getIsDelete());
				baseAppOrganService.save(dept);
				dept = null;
			}
		}
		for (String deptId:userSet) {
			UserInfo[] users = orgService.getUserInfos(deptId);
			for (UserInfo sysuser : users) {
				String userId = sysuser.getUserid();
				BaseAppUser user = baseAppUserService.queryObject(userId);
				if (null == user) {				
					user = new BaseAppUser();
					user.setId(userId);
					user.setUserId(userId);
					user.setAccount(sysuser.getAccount());
					user.setTruename(sysuser.getFullname());
					user.setMobile(sysuser.getMobile());
					user.setUseremail(sysuser.getUserEmail());
					user.setOrganid(sysuser.getOrganId());
					user.setSort(sysuser.getOrderId());
					user.setSex(sysuser.getSex());
					user.setIfSystem(0);
					user.setIfDuty(0);
					user.setIsdelete(sysuser.getIsDelete());
					baseAppUserService.save(user);
					user = null;
				}
			}
		}
	}
}
