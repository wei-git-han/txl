package com.css.txl.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.addbase.orgservice.OrgService;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.SyncOrgan;
import com.css.addbase.orgservice.UserInfo;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.PageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.txl.entity.TxlCollect;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlCollectService;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;
import com.css.txl.utils.ChineseFCUtil;
import com.github.pagehelper.PageHelper;

/**
 * 文件借阅表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-11 17:31:34
 */
@Controller
@RequestMapping("/txlUser")
public class TxlController {

	@Autowired
	private TxlOrganService txlOrganService;
	
	@Autowired
	private TxlUserService txlUserService;
	
	@Autowired
	private TxlCollectService txlCollectService;
	
	@Autowired
	private OrgService service;

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static Long starttime;
	
	public List<TxlUser> userInfos;
	
	public List<Organ> listOrgan = new ArrayList<>();

	public List<UserInfo> listUserInfo = new ArrayList<>();
	
	int i = 0;
	String dept = "";
	
	@RequestMapping(value = "/alluser")
	@ResponseBody
	public void alluser(HttpServletRequest request) {
		userInfos = new ArrayList<TxlUser>();
		List<TxlUser> liInfos=  getAllUser("root");
		JSONArray jsons = new JSONArray();
		jsons = getJson(liInfos);
		Response.json(jsons);
	}
	
	@RequestMapping(value = "/scuser")
	@ResponseBody
	public void scuser(HttpServletRequest request) {
		JSONArray jsons = new JSONArray();
		jsons = getScJson();
		Response.json(jsons);
	}
	
	@RequestMapping(value = "/listuser")
	@ResponseBody
	public void listuser(Integer page, Integer pagesize, String orgid, String searchValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		String orgIds = "";
		if(StringUtils.isNotBlank(searchValue)){
			map.put("search", searchValue);
		}
		if(StringUtils.isNotBlank(orgid) && !StringUtils.equals("root", orgid)){
			orgIds = allOrgIds(orgid);
			map.put("orgIds", orgIds.split(","));
		}
		PageHelper.startPage(page, pagesize);
		List<TxlUser> liInfos = txlUserService.queryList(map);
		fillSc(liInfos);
		PageUtils pageUtil = new PageUtils(liInfos);
		JSONObject json = new JSONObject();
		json.put("total", pageUtil.getTotalCount());
		json.put("page", pageUtil.getCurrPage());
		json.put("rows", liInfos);
		json.put("manager", CurrentUser.getIsManager(appConfig.getAppId(),appConfig.getAppSecret()));
		Response.json(json);
	}
	
	private String allOrgIds(String orgId){
		String ret = "";
		if(StringUtils.isNotBlank(orgId)){
			TxlOrgan org = txlOrganService.queryObject(orgId);
			if(org != null){
				ret += org.getOrganid()+",";
				List<TxlOrgan> list = txlOrganService.getSubOrg(org.getOrganid());
				if(list != null && list.size() > 0){
					for(TxlOrgan organ : list){
						ret += allOrgIds(organ.getOrganid());
					}
				}
			}
		}
		return ret;
	}
	/**
	 * 把当前用户收藏的予以标记
	 * @param liInfos
	 */
	private void fillSc(List<TxlUser> liInfos) {
		if(liInfos==null||liInfos.size()<=0){
			return;
		}
		List<TxlCollect> collects = txlCollectService.getCollect(CurrentUser.getUserId());
		if(collects!=null&&collects.size()>0){
			for(TxlCollect c:collects){
				String scuserId=c.getCollectUserid();
				for(TxlUser user:liInfos){
					String userId=user.getUserid();
					if(scuserId.equals(userId)){
						user.setIsSc("true");
						break;
					}
				}
			}
		}
	}

	@RequestMapping(value = "/orguser")
	@ResponseBody
	public void orguser(HttpServletRequest request, String orgid, String fullname) {
		userInfos = new ArrayList<TxlUser>();
		List<TxlUser> liInfos = new ArrayList<TxlUser>();
		if(null != orgid && !"".equals(orgid) && (null == fullname || "".equals(fullname))) {
			liInfos =  getAllUser(orgid);
		}else if(null != orgid && !"".equals(orgid) && (null != fullname || !"".equals(fullname))) {
			liInfos =  getOthUser(orgid, fullname);
		}else {
			liInfos =  txlUserService.getNameToUser(fullname);
		}
		
		JSONArray jsons = new JSONArray();
		jsons = getJson(liInfos);
		Response.json(jsons);
	}
	
	@RequestMapping(value = "/syncdate")
	@ResponseBody
	public void syncdate() {
		if (starttime == null) {
			//设置消息时间戳
			String time = String.valueOf(System.currentTimeMillis());
			starttime = Long.valueOf(time.substring(0, 10));
		}
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<>();
		// 获取通讯录中已经存在的人员信息
		List<TxlUser> liInfos = txlUserService.queryList(map);
		// 获取通讯录中已经存在的组织机构信息
		List<TxlOrgan> organs = txlOrganService.queryList(map);
		// 如果没有数据，则进行全部同步
		if(liInfos.size() == 0 && organs.size() == 0) {
			List<Organ> organs2 = getAllDept();
			SyncOrgan(organs2);
			List<UserInfo> userInfos = getAllUsers();
			try {
				SyncUser(userInfos);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 如果存在组织机构或者人员信息，则需要进行部分同步
		if(organs.size() != 0 || liInfos.size() != 0) {
			// 同步接口
			String syncInterFace = appConfig.getZuul() + appConfig.getSyncDepartments() + "?starttime=" + starttime +"&access_token=" + appConfig.getAccessToken();
			// 从接口中获取数据
			SyncOrgan syncOrgan = (SyncOrgan) restTemplate.getForObject(syncInterFace, SyncOrgan.class, new Object[0]);
			starttime = syncOrgan.getTimestamp();
			List<UserInfo>  user = syncOrgan.getUser();
			try {
				SyncUser(user);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<Organ> organs2 = syncOrgan.getOrg();
			SyncOrgan(organs2);
		}
		json.put("starttime", starttime);
		
		Response.json(json);
	}
	
	public List<TxlUser> getOthUser(String id, String fullname){
		List<TxlOrgan> organs = txlOrganService.getSubOrg(id);
		List<TxlUser> sysUsers = txlUserService.getOthUsers(id, fullname);
		for (TxlUser sysUser:sysUsers) {
			String depts ="";
			if("root".equals(id)) {
				dept = txlOrganService.queryObject(id).getOrganname();
			}else {
				TxlOrgan wrgan = txlOrganService.queryObject(sysUser.getOrganid());
				while(!"root".equals(wrgan.getOrganid())){
					if("".equals(depts)) {
						depts = wrgan.getOrganname();
					}else {
						depts = depts + "," + wrgan.getOrganname();
					}
					wrgan = txlOrganService.queryObject( wrgan.getFatherid());
				}
				String[] deptL = depts.split(",");
				if(deptL.length>1) {
					dept = deptL[deptL.length-1] + " | " +deptL[deptL.length-2];
				}else if(deptL.length == 1){
					dept = deptL[deptL.length-1 ];
				}
			}
			if(null != sysUser.getPost() && !"".equals(sysUser.getPost())) {
				sysUser.setDept(dept +" | "+sysUser.getPost());
			}else {
				sysUser.setDept(dept);
			}
			userInfos.add(sysUser);
		}
		
		for (TxlOrgan sysOrgan:organs) {
			getOthUser(sysOrgan.getOrganid(), fullname);
		}
		
		return userInfos;
	}
	
	public List<TxlUser> getAllUser(String id){
		List<TxlOrgan> organs = txlOrganService.getSubOrg(id);
		List<TxlUser> sysUsers = txlUserService.getUserInfos(id);
		for (TxlUser sysUser:sysUsers) {
			String depts ="";
			if("root".equals(id)) {
				dept = txlOrganService.queryObject(id).getOrganname();
			}else {
				TxlOrgan wrgan = txlOrganService.queryObject(sysUser.getOrganid());
				while(!"root".equals(wrgan.getOrganid())){
					if("".equals(depts)) {
						depts = wrgan.getOrganname();
					}else {
						depts = depts + "," + wrgan.getOrganname();
					}
					wrgan = txlOrganService.queryObject( wrgan.getFatherid());
				}
				String[] deptL = depts.split(",");
				if(deptL.length>1) {
					dept = deptL[deptL.length-1] + " | " +deptL[deptL.length-2];
				}else if(deptL.length == 1){
					dept = deptL[deptL.length-1 ];
				}
			}
			if(null != sysUser.getPost() && !"".equals(sysUser.getPost())) {
				sysUser.setDept(dept +" | "+sysUser.getPost());
			}else {
				sysUser.setDept(dept);
			}
			userInfos.add(sysUser);
		}
		
		for (TxlOrgan sysOrgan:organs) {
			getAllUser(sysOrgan.getOrganid());
		}
		
		return userInfos;
	}
	
	/**
	 * 返回首字母
	 */
	private static String getPYIndexStr(String strChinese,boolean bUpCase) {
		try {
			StringBuffer buffer = new StringBuffer();
			byte b[] = strChinese.getBytes("GBK");//把中文转化为byte数组
			for(int i=0; i<b.length; i++) {
				if((b[i]&255)>128) {
					int char1=b[i++]&255;
					char1 <<=8;
					int chart =char1+(b[i]&255);
					buffer.append(getPYIndexChar((char)chart,bUpCase));
					continue;
				}
				char c =(char)b[i];
				if(!Character.isJavaIdentifierPart(c))
					c='A';
				buffer.append(c);
			}
			return buffer.toString();
		}catch (Exception e) {
		}
		return null;
	}
	/**
	 * 得到首字母
	 */
	private static char getPYIndexChar(char strChinese,boolean bUpCase) {
		int charGBK = strChinese;
		char result;
		/*if(charGBK>=45217 && charGBK <=45252)
			result = 'A';
		else*/
		if(charGBK>=45217 && charGBK <=45252)
			result = 'A';
		else if(charGBK>=45253 && charGBK <=45760)
			result = 'B';
		else if(charGBK>=45761 && charGBK <=46317)
			result = 'C';
		else if(charGBK>=46318 && charGBK <=46825)
			result = 'D';
		else if(charGBK>=46826 && charGBK <=47009)
			result = 'E';
		else if(charGBK>=47010 && charGBK <=47296)
			result = 'F';
		else if(charGBK>=47297 && charGBK <=47613)
			result = 'G';
		else if(charGBK>=47614 && charGBK <=48118)
			result = 'H';
		else if(charGBK>=48119 && charGBK <=49061)
			result = 'J';
		else if(charGBK>=49062 && charGBK <=49323)
			result = 'K';
		else if(charGBK>=49324 && charGBK <=49895)
			result = 'L';
		else if(charGBK>=49896 && charGBK <=50370)
			result = 'M';
		else if(charGBK>=50371 && charGBK <=50613)
			result = 'N';
		else if(charGBK>=50614 && charGBK <=50621)
			result = 'O';
		else if(charGBK>=50622 && charGBK <=50905)
			result = 'P';
		else if(charGBK>=50906 && charGBK <=51386)
			result = 'Q';
		else if(charGBK>=51387 && charGBK <=51445)
			result = 'R';
		else if(charGBK>=51446 && charGBK <=52217)
			result = 'S';
		else if(charGBK>=52218 && charGBK <=52697)
			result = 'T';
		else if(charGBK>=52698 && charGBK <=52979)
			result = 'W';
		else if(charGBK>=52980 && charGBK <=53688)
			result = 'X';
		else if(charGBK>=53689 && charGBK <=54480)
			result = 'Y';
		else if(charGBK>=54481 && charGBK <=55289)
			result = 'Z';
		else
			/*result = (char)(65+(new Random()).nextInt(25));*/
			result = 1;
		if(!bUpCase) {
			result =Character.toLowerCase(result);
		}
		return result;
	}
	
	
	public JSONArray getJson(List<TxlUser> liInfos) {
		// 获取收藏
		List<TxlUser> scList = new ArrayList<TxlUser>();
		List<TxlCollect> collects = txlCollectService.getCollect(CurrentUser.getUserId());
		for (TxlCollect collect : collects) {
			TxlUser txlUser = txlUserService.queryObject(collect.getCollectUserid());
			txlUser.setIsSc("true");

			String depts = "";
			if ("root".equals(txlUser.getOrganid())) {
				dept = txlOrganService.queryObject(txlUser.getOrganid()).getOrganname();
			} else {
				TxlOrgan wrgan = txlOrganService.queryObject(txlUser.getOrganid());
				while (!"root".equals(wrgan.getOrganid())) {
					if ("".equals(depts)) {
						depts = wrgan.getOrganname();
					} else {
						depts = depts + "," + wrgan.getOrganname();
					}
					wrgan = txlOrganService.queryObject(wrgan.getFatherid());
				}
				String[] deptL = depts.split(",");
				if (deptL.length > 1) {
					dept = deptL[deptL.length - 1] + " | " + deptL[deptL.length - 2];
				} else if (deptL.length == 1) {
					dept = deptL[deptL.length - 1];
				}
			}
			if (null != txlUser.getPost() && !"".equals(txlUser.getPost())) {
				txlUser.setDept(dept + " | " + txlUser.getPost());
			} else {
				txlUser.setDept(dept);
			}

			scList.add(txlUser);
		}
		JSONArray jsons = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("text", "收藏");
		json.put("type", "sc");
		json.put("children", scList);
		jsons.add(json);

		String[] str = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "I", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < str.length; i++) {
			JSONObject json2 = new JSONObject();
			List<TxlUser> zmList = new ArrayList<TxlUser>();
			for (TxlUser txlUser : liInfos) {
				/*
				 * String regEx = "[\\u4e00-\\u9fa5]"; Pattern p =Pattern.compile(regEx);
				 * Matcher m =p.matcher(txlUser.getFullname());
				 */
				StringBuffer sb = new StringBuffer();
				for (int m = 0; m < txlUser.getFullname().length(); m++) {
					if ((txlUser.getFullname().charAt(m) + "").getBytes().length > 1) {
						sb.append(txlUser.getFullname().charAt(m));
					}
				}
				char word[] = sb.toString().toCharArray();
				String firstStr = String.valueOf(word[0]);
				String firstEng = getPYIndexStr(firstStr, true);
				if (str[i].equals(firstEng)) {
					txlUser.setIsSc("false");
					for (TxlUser txlUser2 : scList) {
						if (txlUser2.getUserid().equals(txlUser.getUserid())) {
							txlUser.setIsSc("true");
						}
					}
					zmList.add(txlUser);
				}
			}
			if (zmList.size() > 0) {
				json2.put("text", "联系人 " + str[i]);
				json2.put("type", str[i]);
				json2.put("children", zmList);
				jsons.add(json2);
			}
		}
		return jsons;
	}
	
	
	public JSONArray getScJson() {
		//获取收藏
		List<TxlUser> scList= new ArrayList<TxlUser>();
		List<TxlCollect> collects = txlCollectService.getCollect(CurrentUser.getUserId());
		for(TxlCollect collect:collects) {
			TxlUser txlUser = txlUserService.queryObject(collect.getCollectUserid());
			if(txlUser==null){continue;}
			txlUser.setIsSc("true");
			
			String depts ="";
			if("root".equals(txlUser.getOrganid())) {
				dept = txlOrganService.queryObject(txlUser.getOrganid()).getOrganname();
			}else {
				TxlOrgan wrgan = txlOrganService.queryObject(txlUser.getOrganid());
				while(!"root".equals(wrgan.getOrganid())){
					if("".equals(depts)) {
						depts = wrgan.getOrganname();
					}else {
						depts = depts + "," + wrgan.getOrganname();
					}
					wrgan = txlOrganService.queryObject( wrgan.getFatherid());
				}
				String[] deptL = depts.split(",");
				if(deptL.length>1) {
					dept = deptL[deptL.length-1] + " | " +deptL[deptL.length-2];
				}else if(deptL.length == 1){
					dept = deptL[deptL.length-1 ];
				}
			}
			if(null != txlUser.getPost() && !"".equals(txlUser.getPost())) {
				txlUser.setDept(dept +" | "+txlUser.getPost());
			}else {
				txlUser.setDept(dept);
			}
			
			scList.add(txlUser);
		}
		JSONArray jsons = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("text", "收藏");
		json.put("type", "sc");
		json.put("children", scList);
		jsons.add(json);
		return jsons;
	}
	
	/**
	 * 获取所有的组织机构
	 * @return
	 */
	public List<Organ> getAllDept() {
		List<Organ> resultOrgan = new ArrayList<>();
		resultOrgan = getAllOrgan("root", resultOrgan);
		return resultOrgan;
	}
	/**
	 * 获取所有的组织机构
	 * @return
	 */
	public List<Organ> getAllOrgan(String id, List<Organ> resultOrgan){
		if("root".equals(id)) {
			Organ organ = service.getOrgan(id);
			resultOrgan.add(organ);
		}
		Organ[] organs = service.getSubOrg(id);
		for (Organ sysOrgan:organs) {
			resultOrgan.add(sysOrgan);
			getAllOrgan(sysOrgan.getOrganId(), resultOrgan);
		}
		return resultOrgan;
	}
	
	public List<UserInfo> getAllUsers() {
		List<UserInfo> resultUserInfo = new ArrayList<>();
		resultUserInfo=  getAllUsers("root", resultUserInfo);
		return resultUserInfo;
	}
	
	public List<UserInfo> getAllUsers(String id, List<UserInfo> resultUserInfo){
		Organ[] organs = service.getSubOrg(id);
		UserInfo[] sysUsers = service.getUserInfos(id);
		for (UserInfo sysUser:sysUsers) {
			resultUserInfo.add(sysUser);
		}
		for (Organ sysOrgan:organs) {
			getAllUsers(sysOrgan.getOrganId(), resultUserInfo);
		}
		return resultUserInfo;
	}
	
	public void SyncOrgan(List<Organ> organs){
		for(Organ organ:organs) {
			
			if(StringUtils.equals("0", organ.getType())) {
				txlOrganService.delete(organ.getOrganId());
			} else if (StringUtils.equals("1", organ.getType())){
				TxlOrgan txlOrgan = new TxlOrgan();
				txlOrgan.setCode(organ.getCode());
				txlOrgan.setIsdelete(String.valueOf(organ.getIsDelete()));
				txlOrgan.setOrderid(String.valueOf(organ.getOrderId()));
				txlOrgan.setDn(organ.getDn());
				txlOrgan.setFatherid(organ.getFatherId());
				txlOrgan.setOrganid(organ.getOrganId());
				txlOrgan.setOrganname(organ.getOrganName());
				txlOrgan.setOrguuid(organ.getOrguuid());
				txlOrgan.setType(organ.getType());
				txlOrgan.setTimestamp(organ.getTimestamp());
				TxlOrgan txlOrgantemp = txlOrganService.queryObject(organ.getOrganId());
				if (txlOrgantemp == null) {
					txlOrganService.save(txlOrgan);
				} else {
					txlOrganService.update(txlOrgan);
				} 
			}else {
				TxlOrgan txlOrgan = new TxlOrgan();
				txlOrgan.setCode(organ.getCode());
				txlOrgan.setIsdelete(String.valueOf(organ.getIsDelete()));
				txlOrgan.setOrderid(String.valueOf(organ.getOrderId()));
				txlOrgan.setDn(organ.getDn());
				txlOrgan.setFatherid(organ.getFatherId());
				txlOrgan.setOrganid(organ.getOrganId());
				txlOrgan.setOrganname(organ.getOrganName());
				txlOrgan.setOrguuid(organ.getOrguuid());
				txlOrgan.setType(organ.getType());
				txlOrgan.setTimestamp(organ.getTimestamp());
				txlOrganService.save(txlOrgan);
			}
		}
    }
    
	public void SyncUser(List<UserInfo> userInfos) throws ParseException{
		for(UserInfo userInfo: userInfos) {
			if(StringUtils.equals("0", userInfo.getType())) {
				txlUserService.delete(userInfo.getUserid());
			}else if(StringUtils.equals("1", userInfo.getType())) {
				TxlUser txlUser = new TxlUser();
				txlUser.setIsdelete(String.valueOf(userInfo.getIsDelete()));
				txlUser.setOrderid(String.valueOf(userInfo.getOrderId()));
				txlUser.setAccount(userInfo.getAccount());
				txlUser.setDept(userInfo.getDept());
				txlUser.setPost(userInfo.getDuty());
				txlUser.setTelephone(userInfo.getTel());
				if(null != userInfo.getFullname() && !"".equals(userInfo.getFullname())) {
					txlUser.setFullname(userInfo.getFullname());
					if(userInfo.getFullname().indexOf("首长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"SZ");
					}else if(userInfo.getFullname().indexOf("局长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"JZ");
					}else if(userInfo.getFullname().indexOf("处长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"CZ");
					}else {
						txlUser.setPyName(ChineseFCUtil.cn2py(userInfo.getFullname()).toUpperCase());
					}
				}
				txlUser.setMobile(userInfo.getMobile());
				txlUser.setOrganid(userInfo.getOrganId());
				txlUser.setPassword(userInfo.getPassword());
				txlUser.setSeclevel(userInfo.getSecLevel());
				txlUser.setSex(userInfo.getSex());
				txlUser.setUseremail(userInfo.getUserEmail());
				txlUser.setUserid(userInfo.getUserid());
				TxlUser txlUsertemp = txlUserService.queryObject(userInfo.getUserid());
                if (txlUsertemp == null) {
                	txlUserService.save(txlUser);
                } else {
                	txlUserService.update(txlUser);
                }
			}else {
				//人员新增
				TxlUser txlUser = new TxlUser();
				txlUser.setIsdelete(String.valueOf(userInfo.getIsDelete()));
				txlUser.setOrderid(String.valueOf(userInfo.getOrderId()));
				txlUser.setAccount(userInfo.getAccount());
				txlUser.setDept(userInfo.getDept());
				txlUser.setPost(userInfo.getDuty());
				txlUser.setTelephone(userInfo.getTel());
				if(null != userInfo.getFullname() && !"".equals(userInfo.getFullname())) {
					txlUser.setFullname(userInfo.getFullname());
					if(userInfo.getFullname().indexOf("首长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"SZ");
					}else if(userInfo.getFullname().indexOf("局长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"JZ");
					}else if(userInfo.getFullname().indexOf("处长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"CZ");
					}else {
						txlUser.setPyName(ChineseFCUtil.cn2py(userInfo.getFullname()).toUpperCase());
					}
				}
				txlUser.setMobile(userInfo.getMobile());
				txlUser.setOrganid(userInfo.getOrganId());
				txlUser.setPassword(userInfo.getPassword());
				txlUser.setSeclevel(userInfo.getSecLevel());
				txlUser.setSex(userInfo.getSex());
				txlUser.setUseremail(userInfo.getUserEmail());
				txlUser.setUserid(userInfo.getUserid());
				txlUserService.save(txlUser);
			}
		}
    }
	
}
