package com.css.apporgan.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.orgservice.OrgService;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.appconfig.entity.BaseAppConfig;
import com.css.appconfig.service.BaseAppConfigService;
import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgan.entity.BaseAppUser;
import com.css.apporgan.service.BaseAppOrganService;
import com.css.apporgan.service.BaseAppUserService;
import com.css.apporgan.util.OrgUtil;
import com.css.apporgmapped.service.BaseAppOrgMappedService;
import com.css.base.entity.SSOUser;
import com.css.base.filter.SSOAuthFilter;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.constant.AppConstant;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private BaseAppOrgMappedService baseAppOrgMappedService;
	
	@Autowired
	private OrgService orgService;
	@Autowired
	private BaseAppConfigService baseAppConfigService;
	@Autowired
	private TxlUserService txlUserService;
	@Autowired
	private TxlOrganService txlOrganService;
	
	/**
	 * 获取指定部门下的人员列表
	 * @param organId 组织机构Id
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public void getList(String organId) {
		if (StringUtils.isEmpty(organId)) {
			organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		}
		List<BaseAppUser> users = baseAppUserService.findByOrganid(organId);
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
	 * 加载全部人员树
	 * @return
	 */
	@RequestMapping(value = "/allTree")
	@ResponseBody
	public Object getAllUserTree() {
		List<TxlUser> txlUsers = txlUserService.queryList(null);
		List<TxlOrgan> txlOrgans = txlOrganService.queryList(null);
		//List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		//List<BaseAppUser> users = baseAppUserService.queryList(null);
		//JSONObject list =OrgUtil.getUserTree(organs, users);
		JSONObject list = OrgUtil.getUserTree(txlOrgans, txlUsers);
		return list;
	}
	
	/**
	 * 获取当前登录人所在部门的人员树
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getUserTree() {
		//String organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		//List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		//List<BaseAppUser> users = baseAppUserService.queryList(null);
		String organId = txlOrganService.getBarOrgIdByUserId(CurrentUser.getUserId());
		List<TxlUser> users = txlUserService.queryList(null);
		List<TxlOrgan> organs = txlOrganService.queryList(null);
		if (StringUtils.isNotEmpty(organId)) {
			JSONObject list=  OrgUtil.getUserTree(organs, users, organId);
			return list;
		} else {
			JSONObject list=  OrgUtil.getUserTree(organs, users);
			return list;
		}
	}
	/**
	 * 获取当前登录人所在部门的人员树指定不显示的人员
	 * @return
	 */
	@RequestMapping(value = "/treePart")
	@ResponseBody
	public Object getUserTreePart() {
		/*String organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		List<BaseAppUser> users = baseAppUserService.queryList(null);*/
		String organId = txlOrganService.getBarOrgIdByUserId(CurrentUser.getUserId());
		List<TxlUser> users = txlUserService.queryList(null);
		List<TxlOrgan> organs = txlOrganService.queryList(null);
		String[] hideUserIds = {CurrentUser.getUserId()};
		if (StringUtils.isNotEmpty(organId)) {
			JSONObject list=  OrgUtil.getUserTree(organs, users, organId, null,null,hideUserIds,null);
			return list;
		} else {
			JSONObject list=  OrgUtil.getUserTree(organs, users, organId, null,null,hideUserIds,null);
			return list;
		}
	}
	/**
	 * 根据业务配置筛选人员树
	 * @return
	 */
	@RequestMapping(value = "/treeBySet")
	@ResponseBody
	public Object getUserTreeBySet() {
		/*String organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		List<BaseAppUser> users = baseAppUserService.queryListBySet(null);*/
		String organId = txlOrganService.getBarOrgIdByUserId(CurrentUser.getUserId());
		List<TxlUser> users = txlUserService.queryList(null);
		List<TxlOrgan> organs = txlOrganService.queryList(null);
		if (StringUtils.isNotEmpty(organId)) {
			JSONObject list=  OrgUtil.getUserTree(organs, users, organId);
			return list;
		} else {
			JSONObject list=  OrgUtil.getUserTree(organs, users);
			return list;
		}
//		JSONObject list=  OrgUtil.getUserTree(organs, users, organId,"1");
	}
	/**
	 * 根据人员所在局或者部获取加载人员树，部级加载全部人员
	 * @return
	 */
	@RequestMapping(value = "/treeByPost")
	@ResponseBody
	public Object getUserTreeByPosition() {
		String ret=baseAppOrgMappedService.getAppLevelByType(AppConstant.APP_DZBMS);
		List<TxlUser> users = txlUserService.queryList(null);
		List<TxlOrgan> organs = txlOrganService.queryList(null);
		if("true".equals(ret)){
			JSONObject list = OrgUtil.getUserTree(organs,users);
			return list;
		}else{
			String organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
			JSONObject list=  OrgUtil.getUserTree(organs,users,organId);
			return list;
		}
	}
	
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/userInfo")
    public void getUserInfo(){
    	JSONObject userJson = new JSONObject();
    	SSOUser ssoUser = SSOAuthFilter.getSUser();
    	userJson.put("result","error");
    	if(ssoUser != null){
    		userJson = (JSONObject) JSONObject.toJSON(ssoUser);
    		userJson.put("result","success");
    	}
    	Response.json(userJson);
    }
	

	/**
	 * 导入系统部门及其所属子部门和人员
	 * @param deptIds
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
	 * @param  deptSet
	 */
	private void importUsers(Set<String> deptSet,Set<String> userSet) {
		//
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
			} else {
				dept = new BaseAppOrgan();
				dept.setId(organ.getOrganId());
				dept.setName(organ.getOrganName());
				dept.setParentId(organ.getFatherId());
				dept.setTreePath(organ.getP());
				dept.setSort(organ.getOrderId());
				dept.setDeptOfficer(-1);//默认值为-1， 0-处室， 1-局 FIXME 如何确定部门级别？
				dept.setIsdelete(organ.getIsDelete());
				baseAppOrganService.update(dept);
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
					user.setIsdelete(sysuser.getIsDelete());
					baseAppUserService.save(user);
					user = null;
				} else {
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
					user.setIsdelete(sysuser.getIsDelete());
					baseAppUserService.update(user);
					user = null;
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/getToken")
    public void getToken(){
    String token=SSOAuthFilter.getToken();
    Response.json("result",token);
	}
	
	/**
	 * 仅加载办公厅保密档案室人员 
	 * @return
	 */
	@RequestMapping(value = "/getBmdasUserTree")
	@ResponseBody
	public Object getBmdasUserTree() {
		JSONObject list=new JSONObject();
		BaseAppConfig conf =baseAppConfigService.queryObject("bmdas_orgid");
		if(conf!=null) {
			String organId = conf.getValue();
			/*List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
			List<BaseAppUser> users = baseAppUserService.queryList(null);*/
			List<TxlUser> users = txlUserService.queryList(null);
			List<TxlOrgan> organs = txlOrganService.queryList(null);
			if (StringUtils.isNotEmpty(organId)) {
				list=  OrgUtil.getUserTree(organs, users, organId);
				System.out.println("没有设置办公厅保密档案室人员 ");
			}
		}
		return list;
	}
	
	/**
	 * 加载当前人及当前人的部门
	 */
	@ResponseBody
	@RequestMapping("/addInfo")
    public void addInfo(String leaderId){
		//删掉的代码没有调用到
		JSONObject json = new JSONObject();
		json.put("title", "");
		json.put("secretLevel", "密级");
		json.put("secretLevelName", "保密");
		json.put("jjcdId", "1");
		json.put("undertakeDeptId", "");
	    if(StringUtils.isBlank(leaderId)) {
        	json.put("undertakeDept", CurrentUser.getOrgName());
        	json.put("undertakeUser", CurrentUser.getUsername());
        }else {
        	BaseAppUser user = baseAppUserService.queryObject(leaderId);
        	json.put("undertakeDept", baseAppOrganService.queryObject(user.getOrganid()).getName());
        	json.put("undertakeUser", user.getTruename());
        }
		Response.json(json);
	}
}
